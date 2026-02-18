const baseUrl = "http://localhost:8080/api/v1/item";

$(document).ready(function () {
    getAllItem();
});

$("#btn-save-item").on("click", function (event) {
    saveItem();
})
$("#btn-update-item").on("click", function (event) {
    updateItem();
})
$("#btn-delete-item").on("click", function (event) {
    deleteItem();
})

function saveItem() {

    const name = $('#name').val();
    const qoh = $('#qoh').val();
    const price = $('#price').val();

    $.ajax({
        url: baseUrl,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "name": name,
            "qty": qoh,
            "price": price
        }),
        success: function (response) {
            getAllItem();
            clearItem();
            alert(response.message);
        },
        error: function (error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            alert(msg);
        }
    });
}

function updateItem() {

    const id = $('#id').val();
    const name = $('#name').val();
    const qoh = $('#qoh').val();
    const price = $('#price').val();

    $.ajax({
        url: baseUrl,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            "id": id,
            "name": name,
            "qty": qoh,
            "price": price
        }),
        success: function (response) {
            getAllItem();
            clearItem();
            alert(response.message);
        },
        error: function (error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            alert(msg);
        }
    });
}

function deleteItem() {

    const id = $('#id').val();

    $.ajax({
        url: baseUrl + '/' + id,
        method: 'DELETE',
        success: function (response) {
            getAllItem();
            clearItem();
            alert(response.message);
        },
        error: function (error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            alert(msg);
        }
    });
}

function getAllItem() {

    $('#itemTableBody').empty();

    $.ajax({
        url: baseUrl,
        method: 'GET',
        success: function (response) {

            for (let item of response.data) {

                const row =
                    "<tr>" +
                    "<td>" + item.id + "</td>" +
                    "<td>" + item.name + "</td>" +
                    "<td>" + item.price + "</td>" +
                    "<td>" + item.qty + "</td>" +
                    "</tr>";

                $('#itemTableBody').append(row);
            }
        },
        error: function (error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            alert(msg);
        }
    });
}

function clearItem() {
    $('#id').val('');
    $('#name').val('');
    $('#qoh').val('');
    $('#price').val('');
}
