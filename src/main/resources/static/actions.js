/**
 *
 */
$('document').ready(function (){

    userInformation();

     $('#addForm #addButton').on('click', function(event){

         event.preventDefault();

        let user = {
            name: $('#name').val(),
            lastName: $('#lastName').val(),
            age: $('#age').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            roles: $('#addRoles').val()
        };

        fetch('api/add', {
            method: 'post',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        }).then(() => list())
        $('#name').val('');
         $('#lastName').val('');
         $('#age').val('');
         $('#email').val('');
         $('#password').val('');
    });

    $('#editForm #editButton').on('click', function (event){
        event.preventDefault();
        let user = {
            id: $('#editId').val(),
            name: $('#editName').val(),
            lastName: $('#editLastName').val(),
            age: $('#editAge').val(),
            email: $('#editEmail').val(),
            password: $('#editPassword').val(),
            roles: $('#editRoles').val()
        };
        fetch('api/edit', {
            method: 'put',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        }).then(() => list())
            .then(() => userInformation())
    })
})


function deleteData() {
    fetch('/api/delete/' + document.getElementById('deleteId').value, {
        method: 'delete'
    }).then(() => list() );
}

function list() {

    fetch('api/list').then(
        res => {
            res.json().then(
                data => {
                    console.log(data);
                    if (data.length > 0) {
                        let temp = "";
                        data.forEach((itemData) => {
                            let role = "";
                            for(let i = 0; i < itemData.roles.length; i++) {
                                if(itemData.roles[i].role === "ROLE_ADMIN") {
                                    role += "ADMIN ";
                                } else {
                                    role += "USER ";
                                }
                            }
                            temp += "<tr>";
                            temp += "<td>" + itemData.id + "</td>"
                            temp += "<td>" + itemData.name + "</td>"
                            temp += "<td>" + itemData.lastName + "</td>"
                            temp += "<td>" + itemData.age + "</td>"
                            temp += "<td>" + itemData.email + "</td>"
                            temp += "<td>" + role + "</td>"
                            temp += `<td><a id="editButton" href="/api/getUser/${itemData.id}" class="btn btn-info active">Edit</a></td>`
                            temp += `<td><a id="deleteButton" href="/api/getUser/${itemData.id}"class="btn btn-danger active">Remove</a></td></tr>`;
                        });
                        document.getElementById('table-body').innerHTML = temp;

                        $('.table #editButton').on('click',function(event){

                            event.preventDefault();

                            var href = $(this).attr('href');

                            $.get(href, function(user, status){
                                $('#editId').val(user.id);
                                $('#editName').val(user.name);
                                $('#editLastName').val(user.lastName);
                                $('#editAge').val(user.age);
                                $('#editEmail').val(user.email);
                                $('#editPassword').val(user.password);
                            });

                            $('#editModal').modal();
                        });

                        $('.table #deleteButton').on('click',function(event){

                            event.preventDefault();

                            var href = $(this).attr('href');

                            $.get(href, function(user, status){
                                $('#deleteId').val(user.id);
                                $('#deleteName').val(user.name);
                                $('#deleteLastName').val(user.lastName);
                                $('#deleteAge').val(user.age);
                                $('#deleteEmail').val(user.email);
                                $('#deletePassword').val(user.password);
                                $('#deleteButton').val(user.id);
                            });
                            $('#deleteModal').modal();
                        })
                    }
                }
            )
        }
    )
}

function userInformation(){

    fetch("api/userInfo")
        .then(response => response.json())
        .then(user => {
            let body = "";
            let role = "";
            for(let i = 0; i < user.roles.length; i++) {
                if(user.roles[i].role === "ROLE_ADMIN") {
                    role += "ADMIN ";
                } else {
                    role += "USER ";
                }
            }
                body += `<tr>
                <td>${user.id} </td> 
                <td>${user.name} </td> 
                <td>${user.lastName} </td> 
                <td>${user.age}</td>
                <td>${user.email}</td>   
                <td>${role}</td>        
            </tr>`;
            $('#user_email').text(user.email);
            $('#user_roles').text(role);
            document.getElementById("user-info").innerHTML = body;
        });
}