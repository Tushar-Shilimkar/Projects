#################################################
#   Step 1 : Import required libraries
#################################################

from tensorflow.keras.datasets import imdb
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, LSTM, Dense
from tensorflow.keras.preprocessing.sequence import pad_sequences

#################################################
#   Step 2 : Configuration of values
#################################################

VOCAB_SIZE = 10000  # consider most frequent 10000 unique words
MAX_LENTH = 200     # consider maximum 200 words in review

#################################################
#   Step 3 : Load the IMDB dataset
#################################################

print("*"*45)
print("Movie Review Sentiment analysis using LSTM")
print("*"*45)

print("Loading the Dataset...")

(X_train, Y_train), (X_test, Y_test) = imdb.load_data(num_words = VOCAB_SIZE)

print("IMDB dataset loaded successfully")

print("number of training reviews : ",len(X_train))
print("number of testing reviews : ",len(X_test))

#################################################
#   X_train :   Reviews used for training 
#   Y_train :   Actual sentiments of training
#   X_test  :   Reviews used for testing
#   Y_test  :   Actual sentiments of testing

#   Sentiments : 
#   0 -> Negative sentiments
#   1 -> Positive sentiments
#################################################

#################################################
#   Step 4 : Load the word Dictonary
#################################################

word_index = imdb.get_word_index()

#   Dictonary contains mapping of words and its corresponding number 
#   drisham is good movie       -> (20 56 78 43)
#   20  -> drisham
#   56  -> is
#   78  -> good
#   43  -> movie

#################################################
#   Step 5 : Create reverse Dictonary
#################################################

reverse_word_index = {}

for word, index in word_index.items():
    reverse_word_index[index+3] = word          # +3 -> first 3 reserved ahet

#################################################
#   Step 6 : Function to decode the review (number to word)
#################################################

def DecodeReview(encoded_review):
    words = []

    for number in  encoded_review:
        if number >= 3: # ignore first 3
            word = reverse_word_index.get(number,"?")
            words.append(word)

    return " ".join(words)  # join the list of words

#################################################
#   Step 7 : Display Sample reviews
#################################################

print("*"*45)
print("********** Sample Reviews **********")
print("*"*45)

for i in range(3,7):            #   Starting 3 to 7
    review = DecodeReview(X_train[i])

    print("*"*45)

    print("Review number : ",i+1)
    print("Review : ")
    print(review)

    print("*"*45)

    if Y_train[i] == 1:
        print("Sentiment : POSITIVE")
    else:
        print("Sentiment : NEGATIVE")

#################################################
#   Step 8 : Padding
#################################################

X_train_padded = pad_sequences(
    X_train,
    maxlen = MAX_LENTH
)

X_test_padded = pad_sequences(
    X_test,
    maxlen = MAX_LENTH
)

print("Training data shape : ",X_train_padded.shape)
print("Testing data shape : ",X_test_padded.shape)

#################################################
#   Step 9 : Create LSTM model
#################################################

model  = Sequential()

model.add(
    Embedding(
        input_dim=VOCAB_SIZE,
        output_dim=32           #  each word is represented in 32 values
    )
)

model.add(
    LSTM(
        units = 64              # Size of LSTM hidden state
    )
)

model.add(
    Dense(
        units=1,                # one Output
        activation="sigmoid"    # use to produce probablity
    )
)

#   Project Architecture
#   Review -> Embedding -> LSTM -> Dense -> Sigmod -> Positive / Negative

#################################################
#   Step 10 : Compile the model
#################################################

model.compile(
    optimizer = "adam",               # algorithm to update weights
    loss = "binary_crossentropy",     # Loss function
    metrics = ["accuracy"]            # measure classification accuracy
)

print("Model compile succesfully")

#################################################
#   Step 11 : Train the model
#################################################

print("Model Training")

model.fit(
    X_train_padded,         # Input training reviews
    Y_train,                # Actual sentiment labels
    epochs = 3,             # Complete dataset gets processes 3 times
    batch_size = 64,        # Process 64 reviews in one batch
    validation_split = 0.2  # Use 20% traininag for validation
)

print("Model training gets completed")

#################################################
#   Step 12 : Evaluate the model
#################################################

accuracy = model.evaluate(
    X_test_padded,          # Testing Reviews
    Y_test,                 # Actual testing labels
    verbose = 0             # Dont display the process bar
)

print("Testing Accuracy : ",accuracy)

#################################################
#   Step 13 : Predict the Review
#################################################

TEST_REVIEW_NUMBER = 0

original_review = X_test[TEST_REVIEW_NUMBER]
decoded_review = DecodeReview(original_review)

print("Review given to the model : ")
print(decoded_review)

#################################################
#   Step 14 : Get the actual sentiment
#################################################

actual_value = Y_test[TEST_REVIEW_NUMBER]

if actual_value == 1:
    actual_sentiment = "POSITIVE"
else:
    actual_sentiment = "NEGATIVE"

print("Actual Sentiment : ",actual_sentiment)

#################################################
#   Step 15 : Predict the Sentiment
#################################################

review_for_prediction = X_test_padded[TEST_REVIEW_NUMBER : TEST_REVIEW_NUMBER + 1]

prediction = model.predict(
    review_for_prediction,
    verbose = 0
)

probablity = prediction[0][0]

if probablity >= 0.5:
    predicted_sentiment = "POSITIVE"
else:
    predicted_sentiment = "NEGATIVE"

print("*"*45)

print("Final Result")

print("*"*45)

print("Prediction Probablity : ",probablity)
print("Actual Sentiment : ",actual_sentiment)
print("Predicted Sentiment : ",predicted_sentiment)