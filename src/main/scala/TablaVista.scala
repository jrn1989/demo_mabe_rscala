package com.arena

object tablaGUI {
	import scalafx.scene.control.{TableView,TableColumn}
	import scalafx.collections.ObservableBuffer
	import scalafx.beans.property.{ObjectProperty}
	import scalafx.geometry.Insets

	def  defineTablaVista(datosScala:ObservableBuffer[filaDatos]):TableView[filaDatos] = {

				val colVistaTabla1 = new TableColumn[filaDatos,String]("Aparato")
				colVistaTabla1.cellValueFactory=cdf=>ObjectProperty(cdf.value.Aparato)
				//colVistaTabla1.getStyleClass().add("my-text")

				val colVistaTabla2 = new TableColumn[filaDatos,Int]("Año")
				colVistaTabla2.cellValueFactory=cdf=>ObjectProperty(cdf.value.Anio)

				val colVistaTabla3 = new TableColumn[filaDatos,Double]("Prediccion")
				colVistaTabla3.cellValueFactory=cdf=>ObjectProperty(cdf.value.Prediccion)

				val tablaVista = new TableView(datosScala)
				tablaVista.margin=Insets(0, 10, 10, 10)
				tablaVista.columns ++= List(colVistaTabla1,colVistaTabla2,colVistaTabla3)

				return(tablaVista)

	}

}
