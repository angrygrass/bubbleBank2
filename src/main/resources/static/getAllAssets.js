async function getAssetsFromBackEnd() {
    fetch('http://localhost:8080/assets', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Authorization': 'Customer'
        },
    })
        .then(response => {
            console.log(response)
            return response.json() }
        )
        .then(data => {
            console.log(data);
            loadToAssetTable(data);
        })
        .catch((error) => {
            console.error('Foutje', error);
        });
}
