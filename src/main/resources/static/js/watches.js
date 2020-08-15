/*

    <tr>
    <td></td>
    <td></td>
    <td></td>
    <td></td>

    <td>
        <form action="watches/details/{id}" method="post">
        <button class = "btn btn-info">Details</button>
        </form>
    </tr>
    */

const URLS = {
    watches: "/api/watches/all"
}

const toString = (watch) =>
            '<tr>\n' +
            '    <td>${manufacturer}</td>\n' +
            '    <td>${refNumber}</td>\n' +
            '    <td>${price}</td>\n' +
            '    <td>${gender}</td>\n' +
            '\n' +
            '    <td>\n' +
            '        <form action="watches/details/{id}" method="post">\n' +
            '        <button class = "btn btn-info">Details</button>\n' +
            '        </form>\n' +
            '    </tr>';




window.onload = function () {
    fetch(URLS.watches)

        .then(response => response.json())
         .then(watches => {
          let result = '';


            watches.forEach(watch => {
           const watchTable = toString(watch);
           result+= watchTable;
        });
            console.log(result)
console.log(document.getElementById('watches-table'))
        document.getElementById('watches-table').innerHTML = result;
    })
};
