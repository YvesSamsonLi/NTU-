class Collaboration:
    """
    A class representing a co-authorship

    Attributes:
    publication_title (str): The publication title of the article/paper
    coauthors_pid (list): The list of other coauthors in a collaboration
    year (int): The year the publication was released
    publication_type (str): The type of publication (article, inproceedings, etc)

    Methods:

    """

    def __init__(self, publication_title, coauthors_pid, year, publication_type):
        self.publication_title = publication_title
        self.coauthors_pid = coauthors_pid
        self.year = year
        self.publication_type = publication_type
