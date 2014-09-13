var win = Ti.UI.createWindow({
    exitOnClose: true
});

var popup = require('nc.popup');

var btn = Ti.UI.createButton({
    title: 'Show popup'
});
btn.addEventListener('click', function() {
    var menu = popup.createPopupMenu({
        options: ['Item A', 'Item B', 'Item C'],
        view: btn
    });
    menu.addEventListener('click', function(e) {
        alert('Selected item: '+e.index);
    });
    menu.addEventListener('dismiss', function() {
        alert('popup menu has been dismissed');
    });
    menu.show();
});
win.add(btn);

win.open();
