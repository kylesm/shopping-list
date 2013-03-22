import shopping.list.ShoppingList
import shopping.list.ListItem

class BootStrap {

    def init = { servletContext ->
    	ShoppingList.withTransaction { status ->
			def list = new ShoppingList([
				description: 'My shopping list', completed: false
			])
			
			list.items = [
				new ListItem([name: 'Green pepper', quantity: 3, obtained: false]),
				new ListItem([name: 'Butter', quantity: 1, obtained: false]),
				new ListItem([name: 'Frozen pizza', quantity: 2, obtained: false])
			]
		
			list.save()
    	}
    }
    def destroy = {
    }
}
