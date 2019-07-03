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

const Header = () => <h1>TODOOSE</h1>;

ReactDOM.render(<Application/>, document.querySelector("#application"));
