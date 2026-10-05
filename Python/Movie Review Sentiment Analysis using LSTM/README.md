
# Movie Review Sentiment Analysis using LSTM

A beginner-friendly deep learning project that classifies IMDB movie reviews as **POSITIVE** or **NEGATIVE** using an Embedding + LSTM network built with TensorFlow/Keras.

**Script:** `LSTMModel .py`

---

## Features

- Loads the built-in **IMDB dataset** (no manual download of data needed)
- Decodes integer-encoded reviews back into readable text
- Pads reviews to a fixed length
- Trains a simple **Embedding -> LSTM -> Dense (sigmoid)** model
- Evaluates on the test set
- Predicts the sentiment of a chosen test review

---

## Requirements

- Python 3.9+
- tensorflow

```bash
pip install tensorflow
```

An internet connection is needed on the first run so Keras can download the IMDB dataset and word index.

---

## How to Run

```bash
python "LSTMModel .py"
```

> The filename contains a space before `.py`. Quote it in the terminal, or rename it (for example `LSTMModel.py`).

---

## Configuration

| Variable | Value | Description |
|---|---|---|
| `VOCAB_SIZE` | `10000` | Only the 10,000 most frequent words are kept |
| `MAX_LENTH` | `200` | Each review is padded or truncated to 200 words |
| `TEST_REVIEW_NUMBER` | `0` | Index of the test review used for final prediction |

Training settings: `epochs=3`, `batch_size=64`, `validation_split=0.2`.

---

## Workflow

| Step | Description |
|---|---|
| 1-2 | Import libraries and set configuration |
| 3 | Load IMDB dataset (25,000 training and 25,000 testing reviews) |
| 4-5 | Load word dictionary and build the reverse dictionary |
| 6 | `DecodeReview()` converts numbers back into words |
| 7 | Display sample reviews with their sentiment |
| 8 | Pad sequences to length 200 |
| 9-10 | Build and compile the LSTM model |
| 11 | Train the model |
| 12 | Evaluate on test data |
| 13-15 | Show a review, its actual sentiment and the predicted sentiment |

---

## How Reviews Are Encoded

Each review is a list of integers, where each integer represents a word. In the Keras IMDB dataset, indices 0, 1 and 2 are reserved:

| Index | Meaning |
|---|---|
| 0 | Padding |
| 1 | Start of sequence |
| 2 | Unknown word |

Real words begin from index 3, which is why the reverse dictionary uses `index + 3` and the decoder ignores numbers below 3.

Labels: `0` = Negative, `1` = Positive.

---

## Model Architecture

```
Review -> Embedding(10000, 32) -> LSTM(64) -> Dense(1, Sigmoid) -> Positive / Negative
```

- **Embedding:** each word becomes a vector of 32 values
- **LSTM:** 64 hidden units that learn word order and context
- **Dense + Sigmoid:** outputs a probability between 0 and 1
- **Optimizer:** Adam
- **Loss:** Binary crossentropy
- **Metric:** Accuracy

---

## Prediction Rule

```
probability >= 0.5  ->  POSITIVE
probability <  0.5  ->  NEGATIVE
```

### Sample Output Format

```
Final Result
Prediction Probablity :  <value between 0 and 1>
Actual Sentiment      :  POSITIVE / NEGATIVE
Predicted Sentiment   :  POSITIVE / NEGATIVE
```

---

## Notes

- `model.evaluate()` returns `[loss, accuracy]`, so the "Testing Accuracy" line prints both values as a list. Use `loss, acc = model.evaluate(...)` to print them separately.
- `pad_sequences` pads and truncates at the **start** of a review by default.
- Only 3 epochs are used to keep training short. More epochs may improve accuracy but can lead to overfitting.
- To predict your own text, you would need to convert words to indices using `imdb.get_word_index()` (adding the +3 offset), then pad to 200.

---

## Possible Improvements

- Bidirectional LSTM
- Dropout or recurrent dropout to reduce overfitting
- EarlyStopping callback
- Predicting on custom, user-typed reviews
- Pre-trained embeddings such as GloVe

---
