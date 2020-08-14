/*
<th:block th:each="watch, iter : ${watches}">
    <tr class="row mx-auto">
    <th class="col-md-1 text-center text-white" th:utext="${iter.index + 1}"></th>
    <td class="col-md-3 text-center text-white" th:utext="${watch.manufacturer}"></td>
    <td class="col-md-2 text-center text-white" th:utext="${watch.price}"></td>
    <td class="col-md-2 text-center text-white" th:utext="${watch.gender}"></td>
    <td class="col-md-2 text-center text-white">
    <a th:href="@{/watches/details/{id}(id=${watch.id})}"
class="btn btn-secondary glow-button font-weight-bold text-white">Details</a>
    </td>
    </tr>
    </th:block>*/

const URLS = {
    watches: "/api/watches/all"
}

const toString = (watch) =>

   '    <tr class="row mx-auto">\n' +
   '    <th class="col-md-1 text-center text-white" th:utext="${iter.index + 1}"></th>\n' +
   '    <td class="col-md-3 text-center text-white">${watch.manufacturer}</td>\n' +
   '    <td class="col-md-2 text-center text-white">${watch.price}</td>\n' +
   '    <td class="col-md-2 text-center text-white">${watch.gender}</td>\n' +
   '    <td class="col-md-2 text-center text-white">\n' +
   '    </td>\n' +
   '    </tr>\n';




fetch(URLS.watches)

    .then(response => response.json())
    .then(watches => {
        let result = '';
        let index = 1;

watches.forEach(watch => {
           result +='<td class="col-md-1 text-center text-white">';
            result += index;
            result += '</td>\n';
            result +=  '    <td class="col-md-3 text-center text-white">'
            result += watch.manufacturer;
            result += '</td>\n' +
               '    <td class="col-md-2 text-center text-white">';
            result += watch.price;
            result += '</td>\n' +
               '    <td class="col-md-2 text-center text-white">';
            result += watch.gender;

            index +=1;
        });
            console.log(result)
        document.getElementById('watches-table').append(result);
    });
