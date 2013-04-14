package shopping.list

class AuthorizationService {
	def springSecurityService

	def checkListOwnership(sid) {
		def list = ShoppingList.get(sid)

		if (!list) {
			return [authorized: false, status: 404, errorMessage: "List not found"]
		}

		if (list.owner != springSecurityService.currentUser) {
			return [authorized: false, status: 403, errorMessage: "Unauthorized"]
		}

		return [authorized: true]
	}
}

