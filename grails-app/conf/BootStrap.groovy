import grails.util.Environment

import shopping.list.ShoppingList
import shopping.list.ListItem
import shopping.list.User
import shopping.list.Role
import shopping.list.UserRole

class BootStrap {
	def grailsApplication

    def init = { servletContext ->
		def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
		def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

		def adminUsername = grailsApplication.config.users.defaultAdminUsername
		def adminPassword = grailsApplication.config.users.defaultAdminPassword

		def user = User.findByUsername(adminUsername) ?: new User(username: adminUsername, password: adminPassword, enabled: true, accountExpired: false, accountLocked: false, passwordExpired: false).save(failOnError: true)

		if (!user.authorities.contains(userRole)) {
			UserRole.create(user, userRole)
		}

		switch (Environment.current) {
		case Environment.DEVELOPMENT:
			ShoppingList.withTransaction { status ->
				def list = new ShoppingList([
					description: 'My shopping list', completed: false, items: [], owner: user
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
