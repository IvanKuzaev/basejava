var positions = 0;
var organizations = 0;

function onSubmit() {
    selectAll();
    processSection('EXPERIENCE');
    processSection('EDUCATION');
}

function processSection(name) {
    var fieldset = document.getElementById('section' + name).firstElementChild;
    while(fieldset) {
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = jsonOrganization(fieldset);
        fieldset.appendChild(input);
        fieldset = fieldset.nextElementSibling;
    }
}

function selectAll() {
    markAllSelected(document.getElementsByName("ACHIEVEMENTS")[0]);
    markAllSelected(document.getElementsByName("QUALIFICATIONS")[0]);
}

function markAllSelected(selectTag) {
    selectTag.multiple = true;
    for (var i = 0; i < selectTag.length; i++) {
        selectTag[i].selected = true;
    }
}

function dateObject(stringDate) {
    var parts = stringDate.split('-');
    var year = +parts[0];
    var month = +parts[1];
    var day = +parts[2];
    var dateObject = {};
    dateObject.year = year;
    dateObject.month = month;
    dateObject.day = day;
    return dateObject;
}

function jsonOrganization(fieldset) {
    var organization = {};
    var title = fieldset.firstElementChild.firstElementChild.nextElementSibling.firstElementChild.value;
    var weblink = fieldset.firstElementChild.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.value;
    organization.organization = { "title":title, "webLink":weblink };

    var table = fieldset.firstElementChild.nextElementSibling.firstElementChild;
    var periods = [];
    var tr = table.firstElementChild;
    while (tr) {
        var period = {};
        period.startDate = dateObject(tr.firstElementChild.firstElementChild.firstElementChild.value);
        period.endDate = dateObject(tr.firstElementChild.firstElementChild.nextElementSibling.firstElementChild.value);
        period.title = tr.firstElementChild.nextElementSibling.firstElementChild.firstElementChild.value;
        period.description = tr.firstElementChild.nextElementSibling.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.value;
        periods[periods.length] = period;
        tr = tr.nextElementSibling;
    }
    organization.periods = periods;

    return JSON.stringify(organization);
}

function addItem(items, item) {
    var text = document.getElementById(item).value;
    var newOption = new Option(text, text);
    items.options[items.options.length] = newOption;
}

function removeItem(items) {
    selected = items.options.selectedIndex;
    items.options[selected] = null;
}

function removeOrganization(name) {
    var fieldset = document.getElementById(name);
    fieldset.parentNode.removeChild(fieldset)
}

function addPosition(name) {
    container = document.getElementById(name);
    addPosition0(container);
}

function addPosition0(container) {
    var position = createPosition();
    container.insertBefore(position, container.firstChild);
}

function removePosition(trName) {
    tr = document.getElementById(trName);
    removePosition0(tr);
}

function removePosition0(tr) {
    tr.parentNode.removeChild(tr);
}

function createPosition() {
    var tr = document.createElement('tr');
    tr.id = 'tr' + positions;

    var td1 = document.createElement('td');
    td1.width = '350px';
    td1.vAlign = 'top';
    tr.appendChild(td1);

    var label1 = document.createElement('label');
    label1.innerHTML = 'с <input type="date" required>';
    var label2 = document.createElement('label');
    label2.innerHTML = 'по <input type="date" required>';
    td1.appendChild(label1);
    td1.appendChild(label2);

    var td2 = document.createElement('td');
    tr.appendChild(td2);

    var label3 = document.createElement('label');
    label3.innerHTML = 'позиция <input type="text" placeholder="введите позицию" required>';
    var br = document.createElement('br');
    var label4 = document.createElement('label');
    label4.innerHTML = 'описание <input type="text" placeholder="введите описание">';
    td2.appendChild(label3);
    td2.appendChild(br);
    td2.appendChild(label4);

    var td3 = document.createElement('td');
    td3.vAlign = 'middle';
    td3.align = 'left';
    td3.innerHTML = "<img src='/resumes-webapp/img/delete.png' onclick='removePosition(\"tr" + positions + "\")'>";
    tr.appendChild(td3);

    positions++;
    return tr;
}

function addOrganization(name) {
    container = document.getElementById('section' + name);

    var fieldset = document.createElement('fieldset');
    fieldset.id = 'fieldset' + organizations;

    var legend = document.createElement('legend');
    fieldset.appendChild(legend);

    var string = '';
    string += '<img src="/resumes-webapp/img/delete.png" title="удалить организацию" onclick="removeOrganization(\'fieldset' + organizations + '\')">';
    string += '<label>Организация <input type="text" required></label>';
    string += '<label>Сайт <input type="text"></label>';
    string += '<button type="button" onclick="addPosition(\'table_' + organizations + '\')">Новая позиция</button>';
    legend.innerHTML = string;

    var table = document.createElement('table');
    table.id = 'table_' + organizations;
    fieldset.appendChild(table);

    var tbody = document.createElement('tbody');
    table.appendChild(tbody);

    var tr = createPosition();
    tbody.appendChild(tr);

    container.insertBefore(fieldset, container.firstChild);
}