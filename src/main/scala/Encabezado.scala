package com.arena

object encabezadoGUI {

  import scalafx.scene.control.Label
  import scalafx.scene.text.Font
	import scalafx.scene.layout.{HBox,Region,Priority}
	import scalafx.geometry.{Insets,Pos}
	import scalafx.scene.image.{Image,ImageView}

	def  defineEncabezado(titulo:String,rutaLogo:String):HBox = {
		val tituloApp = new Label {
			text = titulo
			font = new Font("System Bold", 20)
		}		

		val espacioEncabezado = new Region {
			prefHeight=50
			//prefWidth=100
			hgrow = Priority.Always
		}

		val logoApp = new ImageView {
			image = new Image(this.getClass.getResourceAsStream(rutaLogo))
			fitHeight = 50
			fitWidth = 165
			pickOnBounds = true
			preserveRatio = true
		}

		val encabezado = new HBox {
			prefHeight=50
			prefWidth=800
			alignment=Pos.Center
			padding = Insets(10, 10, 10, 15)
			children = List(tituloApp,espacioEncabezado,logoApp)
		}

		return encabezado

	}

}
