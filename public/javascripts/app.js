jQuery(document).ready(function($) {

	// selectors
	/* Selectores de css para javsacript */
	var $leerMsg 	= ".leerMsg",
		$borrarMsg 	= ".eliminarMsg",
		$leidoTxt	= ".leidoTxt"

	// init
	/* Funcionalidades que se ejecutarán siempre al iniciar */


	// triggers
	/* Esto son los triggers, (eventos onALGO), onclick, onchange, mouseover etc) */
	$($leerMsg).on("click", function() {
		var url 		= $(this).data("url")
		var id  		= $(this).data("id")
		leerMensaje(url, id);
	})

	$($borrarMsg).on("click", function() {
		var url 		= $(this).data("url")
		var id  		= $(this).data("id")
		borrarMensaje(url, id);
	})

	// functions
	/* Aquí abajo van las funciones */
	function leerMensaje(url, id) {
		$.ajax({
                url: url,
                type: 'PUT',
                success: function(results) {
                    var parent = $($leidoTxt+"-"+id).parent()
                    if(parent.hasClass("btn-warning")) {
                    	parent.removeClass("btn-warning").addClass("btn-success")
                    	parent.html("Marcar como no leído")
                    } else {
                    	parent.removeClass("btn-success").addClass("btn-warning")
                    	parent.html("Marcar como leído")
                    }
                }
            })
	}

	function borrarMensaje(url, id) {
		$.ajax({
                url: url,
                type: 'DELETE',
                success: function(results) {
                    $(".msg-"+id).fadeOut("slow", function() {
                    	$(".msg-"+id).remove()	
                    })
                }
            });
	}
})
