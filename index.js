class Rock extends React.Component {
    toString() {
        return "💎";
    }

    handleClick() {
        alert(`You clicked ${this}`);
    }

    render() {
        return <button onClick={() => { this.handleClick(); }}>{this.toString()}</button>
    }
}

ReactDOM.render(<Rock />, document.querySelector("#game"));
