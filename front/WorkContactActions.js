
let host = window.location.hostname;
btnFind.onclick = loadContactData;
let contactList = [];

function loadContactData() {
  name = txtNameToFind.value;
  let xhr = new XMLHttpRequest();
  xhr.open('GET', 'http://' + host + ':8085/work/search/' + name);
  xhr.send();
  xhr.onload = function () {
    if (xhr.status == 200) {
      contactList = JSON.parse(xhr.response);
    } else {
      contactList = [];
    }
    updateHTML();
  }
}

function updateHTML() {
  let lista = document.querySelector('ul');
  let html = contactList.map(contact => contactToHTML(contact)).join(' ');
  lista.innerHTML = html;
}

function contactToHTML(contact) {
  let html =
    `<li class="list-group-item">
    <div class="row">
      <div class="col-2">
        <p class="card-text">Nombre completo:</p>
        <p class="card-text">Teléfono:</p>
        <p class="card-text">Domicilio:</p>
        <p class="card-text">Compañía:</p>
        <p class="card-text">Area:</p>
        <p class="card-text">Sector:</p>
    </div> 
    <div class="col-8">
      ${contact.name} ${contact.firstLastName} ${contact.secondLastName}</p>
      ${contact.phone} </p>
      ${contact.address} </p>
      ${contact.companyName} </p>
      ${contact.area} </p>
      ${contact.businessSector} </p>
    </div>
    <div class="col-2 ">
    <div class = "row mb-2" >
      <div class = "col-6 btn btn-primary" data-toggle = "modal" data-target = "#addEditForm" onclick = "editContactData(${contact.id})">
        <i class = "fas fa-pencil-alt"> </i> 
      </div>
    </div> 
    <div class = "row" >
      <div class = "col-6 btn btn-danger" onclick="deleteContactData(${contact.id})">
        <i class = "fas fa-trash"> </i> 
      </div> 
    </div>  
  </div>
  </li>`;
  return html;
}

let inAdditionMode = true;
let currWorkContact;
btnNuevo.addEventListener('click', setAdditionMode);

function setAdditionMode() {
  document.querySelector('#addEditForm h5.modal-title').innerText = "Añadir nuevo contacto";
  inAdditionMode = true;
  document.querySelector('form').reset();
  document.querySelector('#contactName').focus();
}

function saveContactData() {
  let name = document.querySelector('#contactName').value;
  let firstLastName = document.querySelector('#firstLastName').value;
  let secondLastname = document.querySelector('#secondLastName').value;
  let phone = document.querySelector('#phone').value;
  let address = document.querySelector('#address').value;
  let companyName = document.querySelector('#companyName').value;
  let area = document.querySelector('#area').value;
  let businessSector = document.querySelector('#businessSector').value;
  if (inAdditionMode) {
    let workContact = new WorkContact(0, name, firstLastName, secondLastname, phone, address, companyName, area, businessSector);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://' + host + ':8085/work/add');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(workContact));
    xhr.onload = function () {
      if (xhr.status == "200") {
        loadContactData();
        window.alert("Se añadió el contacto con éxito");
      } else {
        window.alert("No se pudo añadir el contacto");
      }
    }
  } else {
    currWorkContact.name = name;
    currWorkContact.firstLastName = firstLastName;
    currWorkContact.secondLastName = secondLastname;
    currWorkContact.phone = phone;
    currWorkContact.address = address;
    currWorkContact.companyName = companyName;
    currWorkContact.area = area;
    currWorkContact.businessSector = businessSector;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', 'http://' + host + ':8085/work/update/' + currWorkContact.id);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(currWorkContact));
    xhr.onload = function () {
      if (xhr.status == "200") {
        loadContactData();
        window.alert("Se modificó el contacto con éxito");
      } else {
        window.alert("No se pudo modificar el contacto");
      }
    }
  }
  $('#addEditForm').modal('hide');
}

function editContactData(id) {
  inAdditionMode = false;
  let workContact = contactList.find(contact => contact.id == id);
  currWorkContact = workContact;
  document.querySelector('#addEditForm h5.modal-title').innerText = "Modificar contacto";
  document.querySelector('#contactName').value = workContact.name;
  document.querySelector('#firstLastName').value = workContact.firstLastName;
  document.querySelector('#secondLastName').value = workContact.secondLastName;
  document.querySelector('#phone').value = workContact.phone;
  document.querySelector('#address').value = workContact.address;
  document.querySelector('#companyName').value = workContact.companyName;
  document.querySelector('#area').value = workContact.area;
  document.querySelector('#businessSector').value = workContact.businessSector;
}

function deleteContactData(id) {
  let xhr = new XMLHttpRequest();
  xhr.open('DELETE', 'http://' + host + ':8085/work/delete/' + id);
  xhr.send(null);
  xhr.onload = function () {
    if (xhr.status == "200") {
      loadContactData();
      window.alert("Se eliminó el contacto con éxito");
    } else {
      window.alert("No se pudo eliminar el contacto");
    }
  }
}