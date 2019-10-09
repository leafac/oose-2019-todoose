function Game() {
    return (
        <div>
            <Player1Choice/>
            <Winner/>
        </div>
    );
}

function Player1Choice() {
    return (
        <div>
            <Header/>
            <p>Player 1: <button>💎</button> <button>🧻</button> <button>✄</button></p>
        </div>
    );
}

function Winner() {
    return (
        <div>
            <Header/>
            <div>
                <p>Player 1 choose 🧻</p>
                <p>Player {1 + 1} choose 🧻</p>
                <p><strong>Player 1 wins!</strong></p>
            </div>
        </div>
    )
}

function Header() {
    return <h1>Rock–Paper–Scissors</h1>;
}

ReactDOM.render(<Game />, document.querySelector("#game"));
