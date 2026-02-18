const customerUrl = "http://localhost:8080/api/v1/customer";
const itemUrl = "http://localhost:8080/api/v1/item";
const orderUrl = "http://localhost:8080/api/v1/order";

let itemsCache = [];

$(function () {
    initOrderPage();
});

// ------------------ Initialization ------------------
function initOrderPage() {
    loadCustomers();
    loadItems();
    getAllOrders();
    $("#order-item-list").empty();
    $("#order-total").text("-");
    $("#order-date").val("");
}

// ------------------ Load Customers ------------------
function loadCustomers() {
    $.get(customerUrl, function (response) {
        const dropdown = $("#order-customer").empty();
        dropdown.append("<option value=''>-- Select Customer --</option>");
        response.data.forEach(c => dropdown.append(`<option value='${c.id}'>${c.name}</option>`));
    });
}

// ------------------ Load Items ------------------
function loadItems() {
    $.get(itemUrl, function (response) {
        itemsCache = response.data; // cache all items for dropdown & price display
    });
}

// ------------------ Load Order History ------------------
function getAllOrders() {
    $.get(orderUrl, function (orders) {
        $('#order-history-list').empty();
        orders.data.forEach(order => {
            const row = `<tr>
                <td>${order.id}</td>
                <td>${order.customerId}</td>
                <td>${order.date}</td>
                <td>${order.total.toFixed(2)}</td>
            </tr>`;
            $('#order-history-list').append(row);
        });
        $("#order-id").text(orders.data.length + 1);
    });
}

// ------------------ Add Item Row ------------------
function addItemRow() {
    const options = itemsCache.map(i => `<option value="${i.id}" data-price="${i.price}">${i.name}</option>`).join("");
    const itemDropdown = `<select class="item-row"><option value=''>-- Select Item --</option>${options}</select>`;
    $("#order-item-list").append(
        `<tr>
            <td>${itemDropdown}</td>
            <td class='price'>0.00</td>
            <td><input type='number' class='qty' min='1' value='1'></td>
            <td class='subtotal'>0.00</td>
            <td><button onclick="deleteItemRow(this)">X</button></td>
        </tr>`
    );
}

// ------------------ Item Selection ------------------
$(document).on("change", ".item-row", function () {
    const row = $(this).closest("tr");
    const selected = $(this).find("option:selected");
    const price = parseFloat(selected.data("price")) || 0;
    row.find(".price").text(price.toFixed(2));
    updateSubTotal(row);
});

// ------------------ Quantity Change ------------------
$(document).on("input", ".qty", function () {
    updateSubTotal($(this).closest("tr"));
});

// ------------------ Delete Row ------------------
function deleteItemRow(btn) {
    $(btn).closest("tr").remove();
    updateGrandTotal();
}

// ------------------ Update Subtotal ------------------
function updateSubTotal(row) {
    const price = parseFloat(row.find(".price").text()) || 0;
    const qty = parseInt(row.find(".qty").val()) || 0;
    const subtotal = price * qty;
    row.find(".subtotal").text(subtotal.toFixed(2));
    updateGrandTotal();
}

// ------------------ Update Grand Total ------------------
function updateGrandTotal() {
    let total = 0;
    $("#order-item-list .subtotal").each(function () {
        total += parseFloat($(this).text()) || 0;
    });
    $("#order-total").text(total.toFixed(2));
}

// ------------------ Clear Items ------------------
function clearItems() {
    initOrderPage();
}

// ------------------ Save Order ------------------
function saveOrder() {
    const customerId = $("#order-customer").val();
    if (!customerId) return alert("Select a customer");

    const date = $("#order-date").val();
    if (!date) return alert("Enter a valid date");

    const rows = $("#order-item-list tr");
    if (rows.length === 0) return alert("Add at least one item");

    const items = [];
    rows.each(function () {
        const itemId = $(this).find(".item-row").val();
        const qty = parseInt($(this).find(".qty").val());
        if (!itemId) return alert("Select a valid item");
        items.push({ itemId, qty });
    });

    const orderData = {
        // id: $("#order-id").text(),
        customerId: customerId,
        date: date,
        items: items
    };

    $.ajax({
        url: orderUrl,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(orderData),
        success: function (response) {
            console.log(response.status);
            console.log(response.message);
            alert(response.message);
            initOrderPage();
        },
        error: function(error) {
            const msg = error.responseJSON?.message || "Something went wrong";
            const stts = error.responseJSON?.status
            console.log(stts);
            console.log(msg);
            alert(msg);
        }
    });
}