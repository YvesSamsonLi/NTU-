import os
import sys
import unittest

import pandas as pd

# Ensure src/ is added to the Python path
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "../src")))

# Now import the module correctly
from sc4022.utils.file_utils import FileUtils


class TestFileUtils(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        """Set up sample data before running tests."""
        cls.sample_data = pd.DataFrame(
            {
                "dblp": [
                    "https://dblp.uni-trier.de/pid/j/AdamJatowt.html",  # ✅ Normal Case
                    "https://dblp.uni-trier.de/pid/a/AliceDoe.html",  # ✅ Normal Case
                    None,  # ❌ Missing link (should be dropped)
                    "https://dblp.uni-trier.de/pid/b/BobSmith.html",  # ✅ Normal Case
                    "https://dblp.uni-trier.de/pid/j/AdamJatowt.html",  # ⚠️ Duplicate (should be removed)
                    "https://dblp.uni-trier.de/pid/c/CarolKing.html",  # ✅ Normal Case
                    "invalid_link",  # ❌ Invalid link (should be ignored)
                    "https://dblp.uni-trier.de/pid/d/DanielLee.html",  # ✅ Normal Case
                    "https://dblp.uni-trier.de/pid/a/AliceDoe.html",  # ⚠️ Duplicate (should be removed)
                ],
                "name": [
                    "Adam Jatowt",
                    "Alice Doe",
                    "Charlie X",
                    "Bob Smith",
                    "Adam Jatowt",
                    "Carol King",
                    "Daniel Lee",
                    "Daniel Lee",
                    "Alice Doe",
                ],
                "country": [
                    "Poland",
                    "USA",
                    "Unknown",
                    "UK",
                    "Poland",
                    "Canada",
                    "Singapore",
                    "SINGAPORE",
                    "Usa",  # Case variations
                ],
                "institution": [
                    "Uni Krakow",
                    "MIT",
                    "Unknown",
                    "Oxford",
                    "Uni Krakow",
                    "Toronto",
                    "NTU",
                    "ntu",
                    "mit",  # Case variations
                ],
                "expertise": [5, 7, None, 9, 5, 3, 6, None, 7],  # Some missing values
            }
        )

        # Extract pid before running clean_df()
        cls.sample_data["pid"] = cls.sample_data["dblp"].apply(
            lambda x: FileUtils.extract_pid_from_dblp(x) if pd.notnull(x) else None
        )

    def test_clean_df(self):
        """Test that clean_df() correctly extracts PIDs, removes duplicates, and drops missing PIDs dynamically."""
        cleaned_df = FileUtils.clean_df(self.sample_data.copy())

        # Ensure no duplicate PIDs exist
        unique_pids = cleaned_df["pid"].unique().tolist()
        self.assertEqual(
            len(cleaned_df), len(unique_pids), "❌ Duplicate PIDs were not removed!"
        )

        # Ensure missing PIDs were removed
        self.assertFalse(
            cleaned_df["pid"].isnull().any(), "❌ There are still missing PIDs!"
        )

        # Ensure only valid PIDs remain
        original_pids = (
            self.sample_data["pid"].dropna().unique().tolist()
        )  # Expected unique PIDs before cleaning
        cleaned_pids = (
            cleaned_df["pid"].unique().tolist()
        )  # Actual unique PIDs after cleaning

        # Check if cleaned PIDs are a subset of the original (duplicates removed)
        self.assertTrue(
            set(cleaned_pids).issubset(set(original_pids)),
            "❌ Unexpected PIDs found in the cleaned dataset!",
        )

        # Print results for debugging
        print(f"✅ Unique PIDs after cleaning: {cleaned_pids}")
        print(f"✅ Original unique PIDs (before cleaning): {original_pids}")


if __name__ == "__main__":
    unittest.main()
