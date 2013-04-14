import grails.util.Environment

import shopping.list.ShoppingList
import shopping.list.ListItem
import shopping.list.User
import shopping.list.Role
import shopping.list.UserRole

class BootStrap {

    def init = { servletContext ->
		switch (Environment.current) {
		case Environment.DEVELOPMENT:
			def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
			def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
			def testUser = User.findByUsername('test') ?: new User(username: 'test', password: 'test', enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save(failOnError: true)

			if (!testUser.authorities.contains(userRole)) {
				UserRole.create(testUser, userRole)
			}

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
