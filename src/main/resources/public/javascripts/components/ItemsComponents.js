class PlusButton extends React.Component {
    handleClick() {
        fetch("/items", { method: "POST" });
    }

    render() {
        return <button className={this.props.className} onClick={() => { this.handleClick(); }}>+</button>;
    }
}

class ItemList extends React.Component {
    constructor(props) {
        super(props);
        // TODO: Add the current situation of whether to show the completed items or not to the state.
        // Hint: It will look like this: ‘showCompleted: false’
        this.state = { items: [] };
    }

    async getDataFromServer() {
        this.setState({ items: await (await fetch("/items")).json() });
        window.setTimeout(() => { this.getDataFromServer(); }, 200);
    }

    componentDidMount() {
        this.getDataFromServer();
    }

    render() {
        // TODO: When rendering the ‘ShowHideCompletedItems’ button say the right word, depending on ‘this.state.showCompleted’. You want to say either “Show” or “Hide”.
        // Hint: Pass the ‘this.state.showCompleted’ as a prop to ‘ShowHideCompletedItems’.
        // TODO: Make the ‘ShowHideCompletedItems’ flip the ‘this.state.showCompleted’.
        // Hint: Pass a function that flips the ‘this.state.showCompleted’ as a prop to ‘ShowHideCompletedItems’.
        return (
            <div>
                <ShowHideCompletedItems />
                <ul>{this.state.items.map(item => <Item key={item.identifier} item={item}/>)}</ul>
            </div>
        );
    }
}

class Item extends React.Component {
    render() {
        // TODO: If ‘showCompleted’ is true, then don’t hide the items.
        return (
            <li style={{display: this.props.item.completed ? "none" : "block"}}>
                <MarkItemAsDoneCheckbox item={this.props.item}/>
                <ItemDescription item={this.props.item}/>
            </li>
        );
    }
}

class ShowHideCompletedItems extends React.Component {
    render() {
        // TODO: When rendering the ‘ShowHideCompletedItems’ button say the right word, depending on ‘this.state.showCompleted’. You want to say either “Show” or “Hide”.
        // Hint: Use the prop passed from ‘ItemList’.
        // TODO: Make the ‘ShowHideCompletedItems’ flip the ‘this.state.showCompleted’.
        // Hint: Use the prop passed from ‘ItemList’.
        return (<button>Show Completed</button>);
    }
}

class MarkItemAsDoneCheckbox extends React.Component {
    handleChange() {
        const formData = new FormData();
        formData.append("completed", !this.props.item.completed);
        fetch(`/items/${this.props.item.identifier}`, { method: "PUT", body: formData });
    }

    render() {
        return <input type="checkbox" onChange={() => { this.handleChange(); } } checked={this.props.item.completed} />
    }
}

class ItemDescription extends React.Component {
    constructor(props) {
        super(props);
        this.state = null;
    }

    handleFocus() {
        this.setState({ description: this.props.item.description });
    }

    handleChange(event) {
        this.setState({ description: event.target.value });
    }

    async handleBlur() {
        const formData = new FormData();
        formData.append("description", this.state.description);
        await fetch(`/items/${this.props.item.identifier}`, { method: "PUT", body: formData });
        this.setState(null);
    }

    render() {
        return (
            <input
                type="text"
                name="description"
                autoComplete="off"
                value={this.state === null ? this.props.item.description : this.state.description}
                onFocus={() => { this.handleFocus(); }}
                onChange={event => { this.handleChange(event); }}
                onBlur={() => { this.handleBlur(); }}
            />
        );
    }
}
