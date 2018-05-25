package com.arena

object filtroGUI {

  import scalafx.scene.control.{Label,ComboBox,RadioButton,ToggleGroup,Button,ListView,ListCell,Cell}
	import scalafx.scene.control.cell._
  import scalafx.scene.text.Font
	import scalafx.scene.layout.{VBox,Priority,Region}
	import scalafx.geometry.{Insets,Pos}
	import scalafx.collections.ObservableBuffer
	import scalafx.Includes._
	import scalafx.scene.Node
	import scalafx.scene.image.{Image,ImageView}

	def  defineFiltro(tablaDatos:ObservableBuffer[filaDatos],botonAplicarFiltro:Button,filtroAparato:ComboBox[String],filtroAnio:ComboBox[Int]):VBox = {

		val textoFiltro = new Label {
			text = "Selección de Covariables"
			font = new Font("System Bold", 15)
		}

		botonAplicarFiltro.prefWidth = 250
		botonAplicarFiltro.margin = Insets(20, 0, 0, 0)

		val textoFiltroAparato = new Label {
			text = "Aparato"
			font = new Font("System Bold", 15)
		}
		filtroAparato.prefWidth = 250
		filtroAparato.promptText = "-"
		filtroAparato.items = new ObservableBuffer[String](tablaDatos.map(_.Aparato).distinct.sorted)	
		filtroAparato += "Todos"

		val textoFiltroAnio = new Label {
			text = "Año"
			font = new Font("System Bold", 15)
		}
		filtroAnio.prefWidth = 250
		filtroAnio.promptText = "-"
		filtroAnio.items = new ObservableBuffer[Int](tablaDatos.map(_.Anio).distinct.sorted)	


		val filtro = new VBox{
			prefHeight=550
			prefWidth=250
			spacing=5
			padding = Insets(10, 10, 10, 15)
			margin = Insets(0, 0, 10, 10)
			children = List(/*textoFiltro,*/textoFiltroAparato,filtroAparato,textoFiltroAnio,filtroAnio,botonAplicarFiltro)
		}
		filtro.getStyleClass().add("vbox")
		textoFiltroAparato.getStyleClass().add("my-text")
		textoFiltroAnio.getStyleClass().add("my-text")



		return filtro
	}
}
