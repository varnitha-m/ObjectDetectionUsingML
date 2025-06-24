# ObjectDetectionApp

JavaFX + Python desktop application for real-time object detection using YOLOv5.

## 📸 Features

- Upload an image via the JavaFX UI
- Send image to local Python backend with YOLOv5
- Receive and display image with bounding boxes and labels

## 🧰 Requirements

### Java (Frontend)
- Java 17+ (e.g., from [AdoptOpenJDK](https://adoptium.net/))
- Maven

### Python (Backend)
- Python 3.8+
- Install dependencies:
  ```bash
  pip install flask opencv-python torch torchvision yolov5
  ```

## 🗂 Project Structure

```
ObjectDetectionApp/
├── pom.xml
├── src/                       # JavaFX UI
│   └── main/java/com/objectdetection/...
├── python-backend/
│   ├── detect_server.py       # Flask server using YOLOv5
│   └── requirements.txt       # (optional)
└── README.md
```

## 🚀 How to Run

### 1. Start the Python Backend

```bash
cd python-backend
python detect_server.py
```

> ⚠️ First-time use will download the YOLOv5 model (approx. 20–100 MB).

### 2. Start the JavaFX App

Open a new terminal:

```bash
cd ObjectDetectionApp
mvn clean javafx:run
```

## 🧠 How It Works

1. User selects an image from disk in the JavaFX app.
2. The image is sent to `localhost:5001` where a Flask server is running YOLOv5.
3. YOLO detects objects and returns an annotated image.
4. JavaFX displays the result.

## 🔧 Notes

- Backend uses `yolov5s` for speed and lightweight performance.
- All inference is done on **CPU** for maximum compatibility.

## 📜 License

MIT License — free to use, modify, and distribute.

---

Built with ❤️ by combining the power of JavaFX and PyTorch.