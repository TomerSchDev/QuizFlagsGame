from flask import Flask, request, jsonify
import sqlite3
import datetime

app = Flask(__name__)
DATABASE = 'quiz_results.db'

def get_db_connection():
    conn = sqlite3.connect(DATABASE)
    conn.row_factory = sqlite3.Row
    return conn

def close_db_connection(conn):
    if conn:
        conn.close()

def init_db():
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS results (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user TEXT NOT NULL,
            timestamp_score DATETIME NOT NULL,
            timestamp_inserted DATETIME DEFAULT CURRENT_TIMESTAMP,
            score INTEGER NOT NULL,
            total_questions INTEGER NOT NULL
        )
    ''')
    conn.commit()
    close_db_connection(conn)

# Initialize the database when the app starts
with app.app_context():
    init_db()

@app.route('/', methods=['GET'])
def home():
    return "Welcome to the Quiz Server!"

@app.route('/submit_result', methods=['POST'])
def submit_result():
    if request.is_json:
        try:
            data = request.get_json()
            user = data.get('user')
            timestamp_score_str = data.get('timestamp_score')
            score = data.get('score')
            total_questions = data.get('total_questions')

            if not user or not timestamp_score_str:
                return jsonify({"error": "Missing user or timestamp_score"}), 400

            try:
                timestamp_score = datetime.datetime.fromisoformat(timestamp_score_str.replace('Z', '+00:00'))
            except ValueError:
                return jsonify({"error": "Invalid timestamp_score format. Use ISO format (e.g., '2025-03-26T17:20:00+00:00')}), 400"})

            conn = get_db_connection()
            cursor = conn.cursor()
            cursor.execute("INSERT INTO results (user, timestamp_score, score, total_questions) VALUES (?, ?, ?, ?)",
                           (user, timestamp_score, score, total_questions))
            conn.commit()
            close_db_connection(conn)

            print(f"Received and saved quiz result - User: {user}, Score: {score}, Total Questions: {total_questions}, Timestamp Score: {timestamp_score}")
            return jsonify({"message": "Result saved successfully!"}), 201
        except Exception as e:
            return jsonify({"error": str(e)}), 400
    else:
        return jsonify({"error": "Request must be JSON"}), 400

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')