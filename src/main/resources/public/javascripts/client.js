const getDataFromServer = async () => {
    const response = await fetch("/items");
    const dataFromServer = await response.json();
    ReactDOM.render(
        <div>
            <button className="plus-at-the-top" onClick={() => { fetch("/items", { method: "POST" })}}>+</button>
            <h1>TODOOSE</h1>
            <ul>
                {
                    dataFromServer.map(item =>
                        <li key={item.identifier}>
                            <form>
                                <input type="checkbox" onChange={() => { fetch(`/items/${item.identifier}`, { method: "DELETE" }); } } />
                                <input type="text" name="description" value={item.description} onChange={event => {
                                    fetch(`/items/${item.identifier}`, { method: "PUT", body: new FormData(event.target.parentElement) });
                                } }/>
                            </form>
                        </li>
                    )
                }
            </ul>
            <button className="plus-at-the-bottom" onClick={() => { fetch("/items", { method: "POST" })}}>+</button>
        </div>,
        document.querySelector("#application")
    );
    window.setTimeout(() => { getDataFromServer(); }, 200);
};
getDataFromServer();
