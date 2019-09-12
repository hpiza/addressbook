btnFind.onclick = loadContactData;

let contactList = [];

function loadContactData() {
  name = txtNameToFind.value;
  let xhr = new XMLHttpRequest();
  xhr.open('GET', 'http://localhost:8085/family/search/' + name);
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
  let contactType = contact.friend ? "Amigo" : "Familia";
  let html =
    `<li class="list-group-item">
    <div class="row">
      <div class="col-2">
        <p class="card-text">Nombre completo:</p>
        <p class="card-text">Teléfono:</p>
        <p class="card-text">Domicilio:</p>
        <p class="card-text">¿Amigo o familia?</p>
    </div> 
    <div class="col-8">
      ${contact.name} ${contact.firstLastName} ${contact.secondLastName}</p>
      ${contact.phone} </p>
      ${contact.address} </p>
      ${contactType} <p>
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
let currFamilyContact;
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
  let friend = document.querySelector('#friend').checked;
  if (inAdditionMode) {
    let familyContact = new FamilyContact(0, name, firstLastName, secondLastname, phone, address, friend);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8085/family/add');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(familyContact));
    xhr.onload = function () {
      if (xhr.status == "200") {
        loadContactData();
        window.alert("Se añadió el contacto con éxito");
      } else {
        window.alert("No se pudo añadir el contacto");
      }
    }
  } else {
    currFamilyContact.name = name;
    currFamilyContact.firstLastName = firstLastName;
    currFamilyContact.secondLastName = secondLastname;
    currFamilyContact.phone = phone;
    currFamilyContact.address = address;
    currFamilyContact.friend = friend;
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', 'http://localhost:8085/family/update/' + currFamilyContact.id);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(currFamilyContact));
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
  let familyContact = contactList.find(contact => contact.id == id);
  currFamilyContact = familyContact;
  document.querySelector('#addEditForm h5.modal-title').innerText = "Modificar contacto";
  document.querySelector('#contactName').value = familyContact.name;
  document.querySelector('#firstLastName').value = familyContact.firstLastName;
  document.querySelector('#secondLastName').value = familyContact.secondLastName;
  document.querySelector('#phone').value = familyContact.phone;
  document.querySelector('#address').value = familyContact.address;
  document.querySelector('#friend').checked = familyContact.friend;
  document.querySelector('#family').checked = !familyContact.friend;
}

function deleteContactData(id) {
  let xhr = new XMLHttpRequest();
  xhr.open('DELETE', 'http://localhost:8085/family/delete/' + id);
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