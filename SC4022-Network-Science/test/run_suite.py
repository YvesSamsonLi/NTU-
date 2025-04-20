import sys
from pathlib import Path

# Add current directory to Python's search path
sys.path.append(str(Path(__file__).parent.resolve()))

from utils import run_unittest_files  # Now this should work

current_dir = Path(__file__).parent.resolve()
print(f"This is current dir: {current_dir}")

if __name__ == "__main__":
    files = [p for p in current_dir.glob("**/test_*.py")]

    print(f"Test files found: {files}")  # Debugging output

    exit_code = run_unittest_files(files)
    exit(exit_code)
