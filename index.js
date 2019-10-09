class Game extends React.Component {
    constructor(props) {
        super(props);
        this.state = {player1Choice: null, player2Choice: null};
    }

    render() {
        let componentToRender;
        if (this.state.player1Choice === null) {
            componentToRender = (
                <Chooser player="1"
                         onClick={(choice) => {
                             this.setState({player1Choice: choice});
                         }}
                />
            );
        } else if (this.state.player2Choice === null) {
            componentToRender = (
                <Chooser player="2"
                         onClick={(choice) => {
                             this.setState({player2Choice: choice});
                         }}
                />
            );
        } else {
            componentToRender = (
                <Result player1Choice={this.state.player1Choice}
                        player2Choice={this.state.player2Choice}
                />
            );
        }
        return (
            <div>
                <h1>Rock–Paper–Scissors</h1>
                {componentToRender}
            </div>
        );
    }
}

function Chooser(props) {
    return (
        <p>
            Player {props.player}:
            <button onClick={() => {
                props.onClick("💎");
            }}>💎</button>
            <button onClick={() => {
                props.onClick("📄");
            }}>📄
            </button>
            <button onClick={() => {
                props.onClick("✂️");
            }}>✂️
            </button>
        </p>
    );
}

function Result(props) {
    let winner;
    if (
        (props.player1Choice === "💎" && props.player2Choice === "✂️") ||
        (props.player1Choice === "✂️" && props.player2Choice === "📄") ||
        (props.player1Choice === "📄" && props.player2Choice === "💎")
    ) {
        winner = "Player 1 wins!";
    } else if (
        (props.player1Choice === "✂️" && props.player2Choice === "💎") ||
        (props.player1Choice === "📄" && props.player2Choice === "✂️") ||
        (props.player1Choice === "💎" && props.player2Choice === "📄")
    ) {
        winner = "Player 2 wins!";
    } else {
        winner = "It’s a draw!";
    }
    return (
        <div>
            <p>Player 1 chose {props.player1Choice}</p>
            <p>Player 2 chose {props.player2Choice}</p>
            <p><strong>{winner}</strong></p>
        </div>
    );
}

ReactDOM.render(<Game/>, document.querySelector("#game"));
