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

win.addEventListener('open', function(){
    var activity = win.getActivity();
    
    if (activity){

        activity.onCreateOptionsMenu = function(e){
            
            e.menu.add({
                title : "Help",
                showAsAction : Ti.Android.SHOW_AS_ACTION_NEVER
            });
            
            e.menu.add({
                title : "About",
                showAsAction : Ti.Android.SHOW_AS_ACTION_NEVER
            });
            
            var add_menu = e.menu.add({
                title : "Add",
                showAsAction : Ti.Android.SHOW_AS_ACTION_ALWAYS
            });
            
            add_menu.addEventListener('click', function(){
                
                // This is how you can use a PopupMenu as a SubMenu
                var menu = popup.createPopupMenu({
                    options: ['User', 'Project', 'Item'],
                    view: add_menu
                });
                menu.addEventListener('click', function(e) {
                    Ti.API.info('Selected item: '+e.index);
                });
                menu.show();
            });
        };
    }
});

win.open();
