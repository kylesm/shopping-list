<html>
<head>
	<title>Shopping list</title>
	<meta name="layout" content="main"/>
	<r:require modules="core,templates,application"/>
</head>

<body>
<div data-role="page" id="home">
    <div data-role="header" data-position="fixed">
		<a href="#newList" id="newListLink" data-rel="popup" data-role="button" data-inline="true" data-transition="pop" data-icon="plus" data-theme="a">Add List</a>
		<h1>Shopping Lists</h1>
		<a href="logout" data-role="button" data-ajax="false" data-icon="gear" data-theme="a">Logout</a>
    </div>
	<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
		<ul data-role="listview" id="shoppingLists">
		</ul>
	</div>

    <div data-role="popup" id="newList" data-overlay-theme="a" data-theme="c" data-dismissible="true" style="max-width:400px;" class="ui-corner-all">
      <div data-role="header" data-theme="a" class="ui-corner-top">
        <h1>New List</h1>
      </div>

      <div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
        <form>
			<label for="add-list-description">Description:</label>
			<input type="text" name="description" id="add-list-description" value="" data-clear-btn="true">
        </form>
        <a href="#" data-role="button" data-inline="true" data-rel="back" data-theme="c">Cancel</a> <a href="#" id="saveListButton" data-role="button" data-inline="true" data-rel="back" data-transition="flow" data-theme="b">Add</a>
      </div>
    </div>
</div>

<div data-role="page" id="listDetail">
	<div data-role="header" data-position="fixed">
		<a href="#home" data-icon="home" id="homeButtonLink">Home</a>
		<h1 id="listName">Shopping List</h1>
		<a href="#popupMenu" data-rel="popup" data-role="button" data-inline="true" data-transition="pop" data-icon="bars" data-theme="a">Menu</a>

      <div data-role="popup" id="popupMenu" data-theme="a">
        <ul data-role="listview" data-inset="true" style="min-width:210px;" data-theme="a">
          <li data-role="divider" data-theme="a">Choose an action</li>

          <li><a href="#addItemDialog" id="addItemLink" data-rel="popup" data-position-to="window" data-inline="true" data-transition="pop" data-icon="plus" data-theme="a">Add Item</a></li>
          <li><a href="#bulkAddItemDialog" id="bulkAddItemLink" data-rel="popup" data-position-to="window" data-inline="true" data-transition="pop" data-icon="plus" data-theme="a">Bulk Add Items</a></li>
          <li><a href="#checkOffAll" id="checkOffAllLink">Check Off All</a></li>
          <li><a href="logout" id="logoutLink" data-ajax="false" data-icon="gear" data-theme="a">Logout</a></li>
        </ul>
      </div>
    </div>

    <div data-role="popup" id="addItemDialog" data-overlay-theme="a" data-theme="c" data-dismissible="false" style="max-width:400px;" class="ui-corner-all">
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
        <a href="#" data-role="button" data-inline="true" data-rel="back" data-theme="c">Cancel</a> <a href="#" id="saveItemButton" data-role="button" data-inline="true" data-rel="back" data-transition="flow" data-theme="b">Add</a>
      </div>
    </div>

    <div data-role="popup" id="bulkAddItemDialog" data-overlay-theme="a" data-theme="c" data-dismissible="false" style="max-width:400px;" class="ui-corner-all">
      <div data-role="header" data-theme="a" class="ui-corner-top">
        <h1>Bulk Add Items</h1>
      </div>

      <div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
        <form>
			<label for="add-items">Items:</label>
			<textarea name="textarea" id="add-items"></textarea>
        </form>
        <a href="#" data-role="button" data-inline="true" data-rel="back" data-theme="c">Cancel</a> <a href="#" id="saveItemsButton" data-role="button" data-inline="true" data-rel="back" data-transition="flow" data-theme="b">Add</a>
      </div>
    </div>

    <form>
      <div data-role="content" id="shoppingList"></div>
    </form>
  </div>
</body>
</html>
