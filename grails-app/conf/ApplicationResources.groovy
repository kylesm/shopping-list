modules = {
    core {
		resource url: '/js/json2.js'
	}

	templates {
		dependsOn 'core'
		resource url: '/js/handlebars.runtime.js'
		resource url: '/js/list.tmpl.js'
		resource url: '/js/listItem.tmpl.js'
    }

	application {
		dependsOn 'core, templates'
		resource url: '/js/shoppinglist.js'
	}
}
