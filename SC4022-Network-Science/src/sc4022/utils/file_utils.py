import os
import random
import re
import time
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import List, Tuple, Union

import pandas as pd
import requests
from tqdm import tqdm  # Add tqdm for better progress tracking

from sc4022.dataset import Collaboration, DataScientist


class FileUtils:
    @staticmethod
    def load_cached_dataset(cache_path: str) -> Union[pd.DataFrame, None]:
        """Loads dataset from cache if available, otherwise returns None."""
        print(f"🔍 Checking for cache at: {cache_path}")
        if os.path.exists(cache_path):
            print(f"📂 Loading cached dataset from {cache_path}...")
            return pd.read_csv(cache_path)
        else:
            print(
                f"⚠️ No cached dataset found at {cache_path}. Proceeding with fresh processing..."
            )
        return None

    @staticmethod
    def read_excel(file_path: str) -> pd.DataFrame:
        """Read the input Excel file and return a DataFrame."""
        print(file_path)
        if os.path.exists(file_path):
            try:
                df = pd.read_excel(file_path, engine="xlrd")
                print(
                    f"✅ Successfully loaded dataset with {df.shape[0]} rows and {df.shape[1]} columns."
                )
                return df
            except Exception as e:
                print(f"❌ Error loading file: {e}")
        else:
            print("❌ Input file not found!")
        return None

    @staticmethod
    def clean_df(df: pd.DataFrame) -> pd.DataFrame:
        """
        Clean dataset: Remove duplicate DBLP links, extract PIDs, validate links,
        handle missing values, and standardize data.
        """
        print("\n🔍 Cleaning Dataset...")

        # **Print initial dataset details**
        print(f"📌 Initial dataset shape: {df.shape}")
        print(f"📌 Columns in dataset: {list(df.columns)}")

        # **Ensure column names do not have unexpected whitespace**
        df.columns = df.columns.str.strip()

        # **Remove rows where 'dblp' is missing before processing**
        missing_dblp_before = df["dblp"].isnull().sum()
        df.dropna(subset=["dblp"], inplace=True)
        print(f"✅ Dropped {missing_dblp_before} rows due to missing DBLP links.")

        # **Display duplicated DBLP links before removal**
        print(f"\n🔗 Checking for duplicate DBLP links...")
        duplicate_dblp = df[df.duplicated(subset="dblp", keep=False)]
        if not duplicate_dblp.empty:
            print(
                f"⚠️ Duplicate DBLP links detected:\n{duplicate_dblp[['name', 'dblp']]}"
            )

        # **Remove duplicate DBLP links**
        df.drop_duplicates(subset="dblp", keep="first", inplace=True)
        df.reset_index(drop=True, inplace=True)
        print(f"✅ Removed duplicate DBLP links. Remaining rows: {len(df)}")

        # **Convert DBLP `.html` links to `.xml` and validate**
        print("\n🔄 Validating and converting DBLP links...")
        pid_list = []
        drop_indices = []
        error_list = []  # Stores failed links for later reporting

        # **Use tqdm for progress tracking**
        for i, link in tqdm(
            enumerate(df["dblp"]),
            total=len(df),
            desc="🔄 Processing DBLP Links",
            leave=True,
            ncols=80,
        ):
            # **Skip invalid links (Avoid printing each time)**
            if not isinstance(link, str) or not link.startswith("http"):
                drop_indices.append(i)
                error_list.append(f"⚠️ Skipped invalid DBLP link: {link}")
                continue

            try:
                response = requests.get(link, timeout=10)

                # **Handle '429 Too Many Requests' error**
                while response.status_code == 429:
                    time.sleep(60)
                    response = requests.get(link, timeout=10)

                if response.status_code != 200:
                    drop_indices.append(i)
                    error_list.append(f"❌ Error {response.status_code} for {link}")
                    continue

                # Convert `.html` links to `.xml`
                new_link = response.url.replace(".html", ".xml")
                df.at[i, "dblp"] = new_link
                pid = re.search(r"pid/([^\.]+)\.html", response.url)

                if pid:
                    pid_list.append(pid.group(1))
                else:
                    drop_indices.append(i)
                    error_list.append(f"❌ Invalid PID extraction for {link}")

            except requests.exceptions.RequestException as e:
                drop_indices.append(i)
                error_list.append(f"❌ Request failed for {link}: {e}")

        # **Drop invalid links**
        df.drop(drop_indices, inplace=True)
        df.reset_index(drop=True, inplace=True)

        # **Assign extracted PIDs**
        df["pid"] = pid_list

        # **Display duplicate PIDs before removal**
        print(f"\n🔍 Checking for duplicate PIDs...")
        duplicate_pids = df[df.duplicated(subset="pid", keep=False)]
        if not duplicate_pids.empty:
            print(
                f"⚠️ Detected duplicate PIDs before removal:\n{duplicate_pids[['pid', 'name', 'dblp']]}"
            )

        # **Remove duplicate PIDs**
        initial_pids = df.shape[0]
        df.drop_duplicates(subset=["pid"], keep="first", inplace=True)
        removed_pids = initial_pids - df.shape[0]
        print(f"✅ Removed {removed_pids} duplicate PIDs. Remaining rows: {len(df)}")

        # **Normalize Expertise Values**
        print("\n🎯 Assigning expertise values...")
        df["expertise"] = [random.randint(1, 10) for _ in range(len(df))]

        # **Standardize Institution and Country Names**
        if "institution" in df.columns:
            df["institution"] = df["institution"].str.strip().str.upper()
        if "country" in df.columns:
            df["country"] = df["country"].str.strip().str.title()

        # **Final dataset summary**
        print(
            f"\n📌 Unique Institutions: {df['institution'].nunique() if 'institution' in df.columns else 'N/A'}"
        )
        print(
            f"📌 Unique Countries: {df['country'].nunique() if 'country' in df.columns else 'N/A'}"
        )
        print(f"✅ Final dataset shape: {df.shape}")

        # **Display all errors after processing is complete**
        if error_list:
            print("\n❌ **Summary of Errors:**")
            for err in error_list:
                print(err)

        return df

    @staticmethod
    def download_xml_files(
        df: pd.DataFrame, target_folder: Union[str, Path], cache_path: str
    ) -> pd.DataFrame:
        """
        Downloads XML files from DBLP links and saves them locally, handling errors.

        Args:
            df (pd.DataFrame): DataFrame containing 'dblp' column with XML URLs.
            target_folder (Union[str, Path]): Directory to save downloaded XML files.
            cache_path: Path to cache cleaned csv

        Returns:
            pd.DataFrame: Cleaned DataFrame with only successfully downloaded records.

        """
        if isinstance(target_folder, str):
            target_folder = Path(target_folder)

        # Ensure output folder exists
        FileUtils.create_folder(target_folder)

        drop_index = []  # Track failed downloads
        print("Beginning to download all xml files......")

        for index, row in df.iterrows():
            xml_link = row["dblp"]

            # Convert .html links to .xml
            if xml_link.endswith(".html"):
                xml_link = xml_link.replace(".html", ".xml")

            response = requests.get(xml_link)

            # Handle 429 Too Many Requests error
            while response.status_code == 429:
                print(f"⚠️ Too many requests! Retrying in 60 seconds for {xml_link}")
                time.sleep(60)
                response = requests.get(xml_link)

            # If request is unsuccessful, log the error and drop the record
            if response.status_code != 200:
                print(f"❌ Error {response.status_code}: Could not fetch {xml_link}")
                drop_index.append(index)
                continue

            # Get text content and verify it's valid XML
            text = response.text
            if (
                "<html" in text.lower()
            ):  # DBLP sometimes returns HTML error pages instead of XML
                print(f"❌ Invalid XML for {xml_link} (HTML detected). Skipping.")
                drop_index.append(index)
                continue

            try:
                root = ET.fromstring(text)  # Attempt to parse XML
                pid = root.attrib["pid"].replace("/", "-")  # Extract and sanitize PID

                # Save the XML content to file please uncomment line 220 to 222 to save xml files
                xml_file_path = target_folder / f"{pid}.xml"
                with open(xml_file_path, "w", encoding="utf-8") as f:
                    f.write(text)

            except ET.ParseError as e:
                print(f"❌ Error parsing XML: {e} for {xml_link}. Skipping.")
                drop_index.append(index)

        # Remove failed records from DataFrame
        df.drop(drop_index, inplace=True)
        df.reset_index(drop=True, inplace=True)

        df.to_csv(cache_path, index=False)

        # saved cleaned dataset to cache
        print(f"📂 Cleaned dataset saved at {cache_path}")

        return df

    @staticmethod
    def create_folder(folder: Union[str, Path]):
        """Create a folder if it doesn't exist."""
        if isinstance(folder, str):
            folder = Path(folder)
        if not folder.exists():
            folder.mkdir(parents=True, exist_ok=True)
            print(f"📂 Created folder: {folder}")

    @staticmethod
    def extract_pid_from_dblp(dblp_url: str) -> str:
        """Extract PID from a DBLP profile URL."""
        if pd.notnull(dblp_url) and "pid/" in dblp_url:
            match = re.search(r"pid/([^\.]+)", dblp_url)
            if match:
                return match.group(1)
        return None

    @staticmethod
    def read_xml_files(
        df: pd.DataFrame, xml_files_folder: Union[str, Path]
    ) -> Tuple[List[DataScientist], float, float]:
        """
        Reads XML files from the specified folder, extracts collaboration data, and constructs DataScientist objects.

        Args:
            df (pd.DataFrame): The cleaned DataFrame containing data scientist metadata.
            xml_files_folder (Union[str, Path]): The directory containing the downloaded XML files.

        Returns:
            Tuple[List[DataScientist], float, float]: A list of DataScientist objects and the min/max publication years.
        """

        # Ensure xml_files_folder is a Path object
        if isinstance(xml_files_folder, str):
            xml_files_folder = Path(xml_files_folder)

        print(f"📂 Reading XML files from: {xml_files_folder}")

        # Extracting the list of valid PIDs from the cleaned dataset
        pid_list = df["pid"].tolist()

        # Retrieve all XML files in the folder
        xml_files_path = list(xml_files_folder.glob("*.xml"))
        print(f"📌 Found {len(xml_files_path)} XML files.")

        data_scientists = []
        min_year = float("inf")
        max_year = float("-inf")

        for xml_file_path in xml_files_path:
            try:
                # Parse the XML file
                tree = ET.parse(str(xml_file_path))
                root = tree.getroot()
                pid = root.attrib["pid"]

                # Retrieve the row in cleaned CSV corresponding to this pid
                row = df[df["pid"] == pid]

                if row.empty:
                    print(
                        f"⚠️ Warning: No matching PID {pid} found in DataFrame. Skipping."
                    )
                    continue  # Skip this file if no matching PID is found

                # Extract metadata
                name = root.attrib["name"]
                country = row["country"].values[0] if "country" in row else "Unknown"
                institution = (
                    row["institution"].values[0] if "institution" in row else "Unknown"
                )
                expertise = (
                    row["expertise"].values[0]
                    if "expertise" in row
                    else random.randint(1, 10)
                )

                collaborations = []

                # Extract scientific papers (if any)
                scientific_papers = root.findall("r")
                for parent_paper in scientific_papers:
                    paper = parent_paper[0]

                    title = (
                        paper.find("title").text
                        if paper.find("title") is not None
                        else "Unknown Title"
                    )
                    year = (
                        int(paper.find("year").text)
                        if paper.find("year") is not None
                        else 0
                    )

                    # Track the minimum and maximum year of publication
                    if year and year < min_year:
                        min_year = year
                    if year and year > max_year:
                        max_year = year

                    publication_type = paper.tag
                    co_authors = paper.findall("author")
                    co_authors_pid = []

                    for co_author in co_authors:
                        co_author_pid = co_author.attrib.get("pid", None)
                        if (
                            co_author_pid
                            and co_author_pid in pid_list
                            and co_author_pid != pid
                        ):
                            co_authors_pid.append(co_author_pid)

                    # Create a Collaboration object for each publication
                    collaboration = Collaboration(
                        title, co_authors_pid, year, publication_type
                    )
                    collaborations.append(collaboration)

                # Create and store a DataScientist object
                data_scientist = DataScientist(
                    name, pid, country, institution, expertise, collaborations
                )
                data_scientists.append(data_scientist)

            except ET.ParseError as e:
                print(f"❌ Error parsing XML file {xml_file_path.name}: {e}")
            except Exception as e:
                print(f"❌ Unexpected error processing {xml_file_path.name}: {e}")

        # If no valid publication years were found, default min/max year to zero
        if min_year == float("inf"):
            min_year = 0
        if max_year == float("-inf"):
            max_year = 0

        print(f"✅ Successfully processed {len(data_scientists)} data scientists.")
        print(f"📅 Publication Year Range: {min_year} - {max_year}")

        return data_scientists, min_year, max_year
