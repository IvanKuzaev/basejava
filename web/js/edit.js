var organizationsW = 0;
var organizationsE = 0;

function markAllSelected(selectTag) {
    selectTag.multiple = true;
    for (var i = 0; i < selectTag.length; i++) {
        selectTag[i].selected = true;
    }
}

function selectAll() {
    markAllSelected(document.getElementsByName("ACHIEVEMENTS")[0]);
    markAllSelected(document.getElementsByName("QUALIFICATIONS")[0]);
}

function addItem(items, item) {
    var text = item.value;
    var newOption = new Option(text, text);
    newOption.name = "asd";
    items.options[items.options.length] = newOption;
}

function removeItem(items) {
    selected = items.options.selectedIndex;
    items.options[selected] = null;
}

function addOrganization(items, item) {
    var text = item.value;
    var newOption = new Option(text, text);
    newOption.name = "asd";
    items.options[items.options.length] = newOption;
}

function removeOrganization(items) {
    selected = items.options.selectedIndex;
    items.options[selected] = null;
}

function createPositionContainer() {
    var container = document.createElement('div');
    var label1 = document.createElement('label');
    label1.innerHTML = "Дата начала";
    var startDate = document.createElement('input');
    startDate.type = "date";
    startDate.name = "startDate" + organizationsW;
    var label2 = document.createElement('label');
    label2.innerHTML = "Дата окончания";
    var endDate = document.createElement('input');
    endDate.type = "date";
    endDate.name = "endDate" + organizationsW;
    var label3 = document.createElement('label');
    label3.innerHTML = "Позиция";
    var position = document.createElement('input');
    position.type = "text";
    position.name = "position" + organizationsW;
    var label4 = document.createElement('label');
    label4.innerHTML = "Описание";
    var description = document.createElement('input');
    position.type = "text";
    position.name = "description" + organizationsW;

    var br = document.createElement('br');
    container.appendChild(label1);
    container.appendChild(organization);
    container.appendChild(label2);
    container.appendChild(weblink);
    container.appendChild(br);

}

function createOrganizationContainer() {
    var container = document.createElement('div');
    addOrganization(container);

    return container;
}

function addOrganization(container) {
    organizationsW++;
    var label1 = document.createElement('label');
    label1.innerHTML = "Организация";
    var organization = document.createElement('input');
    organization.type = "text";
    organization.name = "organizationTitle" + organizationsW;
    var label2 = document.createElement('label');
    label2.innerHTML = "веб-линк";
    var weblink = document.createElement('input');
    weblink.type = "text";
    weblink.name = "webLink" + organizationsW;
    var br = document.createElement('br');
    container.appendChild(label1);
    container.appendChild(organization);
    container.appendChild(label2);
    container.appendChild(weblink);
    container.appendChild(br);
}