import grails.util.Environment

import shopping.list.ShoppingList
import shopping.list.ListItem

class BootStrap {

    def init = { servletContext ->
		switch (Environment.current) {
		case Environment.DEVELOPMENT:
			ShoppingList.withTransaction { status ->
				def list = new ShoppingList([
					description: 'My shopping list', completed: false, items: []
				])

				list.items.addAll([
					new ListItem([name: 'Green pepper', quantity: 3, obtained: false, order: 1]),
					new ListItem([name: 'Butter', quantity: 1, obtained: false, order: 2]),
					new ListItem([name: 'Frozen pizza', quantity: 2, obtained: false, order: 3])
				])

				list.save()
			}

			break
		}
    }
    def destroy = {
    }
}
