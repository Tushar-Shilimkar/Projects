# Industrial Surface Crack Detection using CNN

A binary image classifier built with TensorFlow/Keras that detects whether a surface image contains a **crack** or **no crack**. The script handles the full pipeline: dataset splitting, augmentation, training, evaluation, model saving and single-image prediction.

**Script:** `CNN_Surface_Crack_Detection.py`

---

## Features

- Automatic **70% / 15% / 15%** split into train, validation and test sets
- Image **augmentation** (rotation, zoom, shifts, horizontal flip) on training data only
- 4-block **CNN** with Batch Normalization, MaxPooling and Dropout
- Training **callbacks**: EarlyStopping, ModelCheckpoint, ReduceLROnPlateau
- Accuracy and loss plots
- Test evaluation with **confusion matrix** and **classification report**
- Single image prediction function

---

## Requirements

- Python 3.9+
- tensorflow
- numpy
- matplotlib
- scikit-learn

```bash
pip install tensorflow numpy matplotlib scikit-learn
```

---

## Dataset Structure

Place your original dataset next to the script in this layout:

```
CrackDataset/
├── Positive/    # images WITH cracks
└── Negative/    # images WITHOUT cracks
```

Supported formats: `.jpg`, `.jpeg`, `.png`, `.bmp`, `.webp`

A commonly used public dataset for this task is the *Surface Crack Detection* dataset (Kaggle), which already uses the Positive/Negative folder names.

On each run the script **deletes and recreates** `Processed_CrackDataset/`:

```
Processed_CrackDataset/
├── train/       (Crack, NoCrack)
├── validation/  (Crack, NoCrack)
└── test/        (Crack, NoCrack)
```

Your original images are only copied, never modified.

---

## How to Run

```bash
python Marvellous_CNN_Surface_Crack_Detection.py
```

Plots open in blocking windows. Close each window to let the script continue.

---

## Configuration

| Variable | Default | Description |
|---|---|---|
| `ORIGINAL_DATASET` | `CrackDataset` | Folder holding Positive/Negative images |
| `PROCESSED_DATASET` | `Processed_CrackDataset` | Generated train/val/test folder |
| `IMAGE_SIZE` | `128` | Images resized to 128 x 128 |
| `BATCH_SIZE` | `32` | Images per training batch |
| `EPOCHS` | `15` | Maximum training epochs |
| `RANDOM_SEED` | `42` | Seed for reproducibility |

---

## Workflow

1. Check that the Positive and Negative folders exist and contain images
2. Create the processed folder structure
3. Shuffle and split each class into train / validation / test
4. Build the data generators (augmentation for train, only rescaling for validation and test)
5. Show 6 sample training images
6. Build and compile the CNN
7. Train with callbacks
8. Plot accuracy and loss curves
9. Evaluate on the unseen test set
10. Print confusion matrix and classification report
11. Save the final model
12. Run a prediction on one test image

---

## Model Architecture

```
Input (128 x 128 x 3)
 -> Conv2D(32)  -> BatchNorm -> MaxPool
 -> Conv2D(64)  -> BatchNorm -> MaxPool
 -> Conv2D(128) -> BatchNorm -> MaxPool
 -> Conv2D(256) -> BatchNorm -> MaxPool
 -> Flatten
 -> Dense(256, ReLU) -> Dropout(0.5)
 -> Dense(128, ReLU) -> Dropout(0.3)
 -> Dense(1, Sigmoid)
```

- **Optimizer:** Adam
- **Loss:** Binary crossentropy
- **Metric:** Accuracy

### Callbacks

| Callback | Setting |
|---|---|
| EarlyStopping | Monitors `val_loss`, patience 4, restores best weights |
| ModelCheckpoint | Saves best model by `val_accuracy` |
| ReduceLROnPlateau | Monitors `val_loss`, factor 0.2, patience 2, min LR 1e-5 |

---

## Output Files

| File | Description |
|---|---|
| `Best_Crack_Detection_Model.keras` | Best checkpoint by validation accuracy |
| `Final_Crack_Detection_Model.keras` | Final model after training |
| `Processed_CrackDataset/` | Generated train/validation/test split |

---

## Class Labels

Keras assigns class indices alphabetically:

```
Crack   -> 0
NoCrack -> 1
```

The sigmoid output is therefore the probability of **NoCrack**. The script handles this: `predict_single_image()` reads `train_data.class_indices` and interprets the prediction accordingly, so a value above 0.5 means *No Crack*.

---

## Single Image Prediction

At the end of the script, the first image in `Processed_CrackDataset/test/Crack` is predicted. To predict your own image:

```python
predict_single_image("path/to/your_image.jpg")
```

The function resizes to 128 x 128, normalizes by 255, runs the model and shows the image with the result as its title.

---

## Notes

- The split is random each run, controlled by `RANDOM_SEED`. GPU operations may still cause small run-to-run differences.
- The test generator uses `shuffle=False` so predictions line up with `test_data.classes` for the confusion matrix.
- Training on CPU can be slow for large datasets. A GPU is recommended.

---

## Possible Improvements

- Transfer learning (MobileNetV2, ResNet50)
- Class weights if the dataset is imbalanced
- Grad-CAM to visualize where the model detects cracks
- Export to TFLite for edge or industrial deployment

---

*Developed as part of Marvellous Infosystems training.*
