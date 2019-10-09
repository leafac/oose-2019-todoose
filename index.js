function Game() {
    return (
        <div>
            <h1>Rock–Paper–Scissors</h1>
            <p>Player 1: <button>💎</button> <button>🧻</button> <button>✄</button></p>

            <h1>Rock–Paper–Scissors</h1>
            <div>
                <p>Player 1 choose 🧻</p>
                <p>Player {1 + 1} choose 🧻</p>
                <p><strong>Player 1 wins!</strong></p>
            </div>
        </div>
    );
}

ReactDOM.render(<Game />, document.querySelector("#game"));
