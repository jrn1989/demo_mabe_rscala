package com.arena
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Label,Button,ComboBox,TableView,TableColumn}
import scalafx.scene.layout.BorderPane
import scalafx.scene.image.{Image} 
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{ObjectProperty}
import scalafx.event.ActionEvent
import java.io._

case class filaDatos(Aparato:String, Anio:Int, Prediccion:Double){
  def getAparato() : String = {
    return this.Aparato;
  }
  def getAnio() : Int = {
    return this.Anio;
  }
  def getPrediccion() : Double = {
    return this.Prediccion;
  }
}

object mabeDemo extends JFXApp {
	import com.arena.encabezadoGUI.defineEncabezado
	import com.arena.filtroGUI.defineFiltro
	import com.arena.tablaGUI.defineTablaVista
		
	val datosScala = new ObservableBuffer[filaDatos]()
	val R = org.ddahl.rscala.RClient() // Este objeto da acceso a una sesión de R dentro de una instancia de Scala
	val rutaCodigoR = "source('" + new File(".").getCanonicalPath() + "/src/main/resources/R/prueba.R')" // Ruta donde se encuentra el código de R

	// Función para filtrar datos usando R
	def aplicarFiltroEnR(filtroAparato:ComboBox[String],filtroAnio:ComboBox[Int]):ObservableBuffer[filaDatos]={
		val dfScala = new ObservableBuffer[filaDatos]()
		val codigoR ="dfR<-filtrar('"+filtroAparato.value.apply+"',"+filtroAnio.value.apply+")"
		R.eval(codigoR) //Para evaluar una expresión de R
		val col1 = R.evalS1("dfR$Aparato") //Tipo de dato String
		val col2 = R.evalI1("dfR$Anio") //Tipo de dato Int
		val col3 = R.evalD1("dfR$Prediccion") //Tipo de dato Double

		for ( i <- 0 to (col1.length - 1)) {
			dfScala += new filaDatos(col1(i),col2(i),col3(i))				
		}
		return dfScala
	}

  stage = new PrimaryStage {
    scene = new Scene {
			title="mabe - simplifica tu vida"
			icons += new Image((this.getClass.getResourceAsStream("/mabeIcono.png")))
			stylesheets add getClass.getResource("/estilo.css").toExternalForm
			root = {				
				//R.invoke("imprime",2,2)

				R.eval(rutaCodigoR) //Se ejecuta el script prueba.R, donde se asume que ya se leyeron los datos crudos, se les hizo un 
				//preprocesamiento y se ajustó un modelo. El resultado final se guarda en un dataframe llamado "modelo" del cual
				//se extraen las variables relevantes para ser mostradas en el GUI 
				val col1 = R.evalS1("modelo$Aparato")
				val col2 = R.evalI1("modelo$Anio")
				val col3 = R.evalD1("modelo$Prediccion")

				for ( i <- 0 to (col1.length - 1)) {
					datosScala += new filaDatos(col1(i),col2(i),col3(i))					
				}

				val botonAplicarFiltro = new Button("Aplicar Filtros")
				val filtroAparato = new ComboBox[String]
				val filtroAnio = new ComboBox[Int]

				val pantallaPrincipal =  new BorderPane {
					prefHeight = 600
					prefWidth = 800
					top = defineEncabezado("Demo rscala","/mabeLogo.png")
					left = defineFiltro(datosScala,botonAplicarFiltro,filtroAparato,filtroAnio)
					center = defineTablaVista(datosScala)
				}

				botonAplicarFiltro.onAction = (e:ActionEvent) =>  {
					pantallaPrincipal.center=defineTablaVista(aplicarFiltroEnR(filtroAparato,filtroAnio))
				}

			pantallaPrincipal
			}
			
    }
  }
}
