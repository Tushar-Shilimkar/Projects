# 📦 File Packer Unpacker

A simple Java utility to extract (unpack) files from a custom **"packed" file format**. The packed file contains one or more blocks, each with a small header (`filename size`) followed by the raw bytes of that file. This program reads the header, then extracts each embedded file back to disk.

Comes in two versions:

| Version | File | Description |
|---|---|---|
| 🖥️ CLI | `program733.java` | Console-based, prompts for the packed file path |
| 🪟 GUI | `File_Packer_Unpacker.java` | Swing GUI with Browse + Unpack buttons and a live log |

## ✨ Features

- 📂 Reads packed file headers and extracts each embedded file with its original name
- 🗂️ Handles multiple files packed in a single archive
- ✅ Validates headers (skips invalid/corrupt entries instead of crashing)
- 🔄 Reads exact byte count per file, even for large files (loop-based read, not single-shot)
- 🔒 Properly closes file streams to avoid resource leaks
- 📊 GUI version lets you browse for the packed file and view extraction logs in real time

> Note: The header is read from a fixed 100-byte block, then parsed by splitting on whitespace. The first token is the filename, the second is the file size in bytes.

## 🚀 How to Run

### 🖥️ Console version

```bash
javac program733.java
java program733
```

You'll be prompted to enter the path of the packed file. Extracted files will be created in the current working directory.

### 🪟 GUI version

```bash
javac File_Packer_Unpacker.java
File_Packer_Unpacker.java
```

1. Click **Browse...** to select the packed file
2. Click **Unpack**
3. Extracted files are saved in the same folder as the packed file
4. Progress and results are shown in the log area

## 📁 Project Structure

```
.
├── program733.java             # CLI version
├── File_Packer_Unpacker.java   # GUI version
└── README.md
```

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you'd like to change.

## 📄 License

Free to use and modify for personal or educational purposes.
