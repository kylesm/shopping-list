<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">

<html>
<head>
  <title>Shopping list</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css" type="text/css">
</head>

<body>
  <div data-role="page">
    <div data-role="header">
      <a href="#" data-icon="home">Home</a>

      <h1 id="listName">Shopping List</h1><a href="#popupMenu" data-rel="popup" data-role="button" data-inline="true" data-transition="pop" data-icon="bars" data-theme="a">Menu</a>

      <div data-role="popup" id="popupMenu" data-theme="a">
        <ul data-role="listview" data-inset="true" style="min-width:210px;" data-theme="a">
          <li data-role="divider" data-theme="a">Choose an action</li>

          <li><a href="#popupDialog" id="addItemLink" data-rel="popup" data-position-to="window" data-inline="true" data-transition="pop" data-icon="plus" data-theme="a">Add item...</a></li>

          <li><a href="#checkOffAll" id="checkOffAllLink">Check off all</a></li>
        </ul>
      </div>
    </div>

    <div data-role="popup" id="popupDialog" data-overlay-theme="a" data-theme="c" data-dismissible="false" style="max-width:400px;" class="ui-corner-all">
      <div data-role="header" data-theme="a" class="ui-corner-top">
        <h1>Add Item</h1>
      </div>

      <div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
        <form>
        <label for="add-name">Item:</label>
<input type="text" name="name" id="add-name" value="" data-clear-btn="true">
        <label for="add-quantity">Quantity:</label>
<input type="range" name="quantity" id="add-quantity" value="1" min="1" max="20" step="1" data-highlight="true">
        </form>
        <a href="#" data-role="button" data-inline="true" data-rel="back" data-theme="c">Cancel</a> <a href="#" id="saveItemButton" data-role="button" data-inline="true" data-rel="back" data-transition="flow" data-theme="b">Save</a>
      </div>
    </div>

    <form>
      <div data-role="content" id="shoppingList"></div>
    </form>
  </div><script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript">
</script><script src="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js" type="text/javascript">
</script><script src="js/json2.js" type="text/javascript">
</script><script src="js/handlebars.runtime.js" type="text/javascript">
</script><script src="js/listItem.tmpl.js" type="text/javascript">
</script><script src="js/shoppinglist.js" type="text/javascript">
</script>
</body>
</html>
