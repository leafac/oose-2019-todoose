const getDataFromServer = async () => {
    const response = await fetch("/items");
    const dataFromServer = await response.json();
    const Header = () => <h1>TODOOSE</h1>;
    const PlusButton = (props) => (
        <button className={props.className} onClick={() => { fetch("/items", { method: "POST" })}}>
            +
        </button>
    );
    class ItemList extends React.Component {
        render() {
            return <ul>{dataFromServer.map(item => <Item item={item}/>)}</ul>;
        }
    }
    class Item extends React.Component {
        render() {
            return (
                <li key={this.props.item.identifier}>
                    <form>
                        <MarkItemAsDoneCheckbox item={this.props.item}/>
                        <ItemDescription item={this.props.item}/>
                    </form>
                </li>
            );
        }
    }
    const MarkItemAsDoneCheckbox = (props) => (
        <input type="checkbox" onChange={() => { fetch(`/items/${props.item.identifier}`, { method: "DELETE" }); } } />
    );
    class ItemDescription extends React.Component {
        render() {
            return (
                <input type="text" name="description" value={this.props.item.description} onChange={event => {
                    fetch(`/items/${this.props.item.identifier}`, { method: "PUT", body: new FormData(event.target.parentElement) });
                } }/>
            );
        }
    }
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
    ReactDOM.render(<Application/>, document.querySelector("#application"));
    window.setTimeout(() => { getDataFromServer(); }, 200);
};
getDataFromServer();
