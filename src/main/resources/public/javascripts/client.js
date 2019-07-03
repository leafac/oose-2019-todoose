class Application extends React.Component {
    render() {
        return (
            <div>
                <PlusButton className="plus-at-the-top"/>
                <Header/>
                <ItemList/>
                <PlusButton className="plus-at-the-bottom"/>
            </div>
        );
    }
}

const PlusButton = (props) => (
    <button className={props.className} onClick={() => { fetch("/items", { method: "POST" })}}>
        +
    </button>
);

const Header = () => <h1>TODOOSE</h1>;

class ItemList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {items: []};
    }

    async getDataFromServer() {
        this.setState({items: await (await fetch("/items")).json()});
        window.setTimeout(() => { this.getDataFromServer(); }, 200);
    }

    componentDidMount() {
        this.getDataFromServer();
    }

    render() {
        return <ul>{this.state.items.map(item => <Item key={item.identifier} item={item}/>)}</ul>;
    }
}

class Item extends React.Component {
    render() {
        return (
            <li>
                <MarkItemAsDoneCheckbox item={this.props.item}/>
                <ItemDescription item={this.props.item}/>
            </li>
        );
    }
}

const MarkItemAsDoneCheckbox = (props) => (
    <input type="checkbox" onChange={() => { fetch(`/items/${props.item.identifier}`, { method: "DELETE" }); } } />
);

class ItemDescription extends React.Component {
    constructor(props) {
        super(props);
        this.state = null;
    }

    render() {
        return (
            <input
                type="text"
                name="description"
                value={this.state === null ? this.props.item.description : this.state.description}
                onFocus={() => { this.setState({description: this.props.item.description}); }}
                onChange={event => { this.setState({description: event.target.value }); }}
                onBlur={
                    async () => {
                        const formData = new FormData();
                        formData.append("description", this.state.description);
                        await fetch(`/items/${this.props.item.identifier}`, { method: "PUT", body: formData });
                        this.setState(null);
                    }
                }
            />
        );
    }
}

ReactDOM.render(<Application/>, document.querySelector("#application"));
