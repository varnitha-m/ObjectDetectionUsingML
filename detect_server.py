from flask import Flask, request, send_file
import torch
import cv2
import numpy as np
import uuid
from io import BytesIO
from PIL import Image

app = Flask(__name__)

model = torch.hub.load('ultralytics/yolov5', 'yolov5s', trust_repo=True)

@app.route('/detect', methods=['POST'])
def detect():
    image_bytes = request.data
    nparr = np.frombuffer(image_bytes, np.uint8)
    img_np = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

    results = model(img_np)
    results.render()  # updates results.imgs with boxes and labels

    pil_img = Image.fromarray(results.imgs[0])
    img_io = BytesIO()
    pil_img.save(img_io, 'PNG')
    img_io.seek(0)
    return send_file(img_io, mimetype='image/png')

if __name__ == "__main__":
    app.run(port=5001)