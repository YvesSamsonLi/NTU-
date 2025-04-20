class DataScientist:
    """
    A class representing a data scientist

    Attributes:
    name (str): The name of the data scientist
    pid (str): The list of other coauthors in a collaboration
    country (str): The country where the data scientist is based
    institution (str): The name of the institution
    expertise (int): A numerical integer representing the data scientist's skill
    collaboration (list): A list of publication collaboration

    Methods:

    """

    def __init__(self, name, pid, country, institution, expertise, collaborations):
        self.name = name
        self.pid = pid
        self.country = country
        self.institution = institution
        self.expertise = expertise
        self.collaborations = collaborations
