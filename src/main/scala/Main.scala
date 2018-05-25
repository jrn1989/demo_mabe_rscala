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
		
	val datosScala = new ObservableBuffer[filaDatos]()
	val R = org.ddahl.rscala.RClient()
	val rutaCodigoR = "source('" + new File(".").getCanonicalPath() + "/src/main/resources/R/prueba.R')"
	//val rutaCodigoRFiltos = "source('" + new File(".").getCanonicalPath() + "/src/main/resources/R/filtros.R')"

  stage = new PrimaryStage {
    scene = new Scene {
			title="mabe - simplifica tu vida"
			icons += new Image((this.getClass.getResourceAsStream("/mabeIcono.png")))
			stylesheets add getClass.getResource("/estilo.css").toExternalForm
			root = {				

				R.eval(rutaCodigoR)
				//R.invoke("imprime",2,2)
				val col1 = R.evalS1("modelo$Aparato")
				val col2 = R.evalI1("modelo$Anio")
				val col3 = R.evalD1("modelo$Prediccion")

				for ( i <- 0 to (col1.length - 1)) {
					datosScala += new filaDatos(col1(i),col2(i),col3(i))					
				}
				//R.eval("imprime<-function(x,y){ print(paste('Los valores son',x,'y',y,sep=' ')) }")
				//R.eval("imprime(2,3)")
				//R.eval("source('/home/jrn/Documentos/rscala_test/prueba.R')")

				val botonAplicarFiltro = new Button("Aplicar Filtros")
				val filtroAparato = new ComboBox[String]
				val filtroAnio = new ComboBox[Int]

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

				val pantallaPrincipal =  new BorderPane {
					prefHeight = 600
					prefWidth = 800
					top = defineEncabezado("Demo rscala","/mabeLogo.png")
					left = defineFiltro(datosScala,botonAplicarFiltro,filtroAparato,filtroAnio)
					center = tablaVista
				}

				botonAplicarFiltro.onAction = (e:ActionEvent) =>  {
					val cadenaFiltroR ="df<-filtrar('"+filtroAparato.value.apply+"',"+filtroAnio.value.apply+")"
					println("RESULTADO DE LOS FILTROS APLICADOS")					
					R.eval(cadenaFiltroR)
					val col1Filtrada = R.evalS1("df$Aparato")
					val col2Filtrada = R.evalI1("df$Anio")
					val col3Filtrada = R.evalD1("df$Prediccion")
					val datosScalaFiltro = new ObservableBuffer[filaDatos]()
					for ( i <- 0 to (col1Filtrada.length - 1)) {
						datosScalaFiltro += new filaDatos(col1Filtrada(i),col2Filtrada(i),col3Filtrada(i))		
						println(col1Filtrada(i),col2Filtrada(i).toString,col3Filtrada(i).toString)			
					}

					val colVistaTabla1Filtrada = new TableColumn[filaDatos,String]("Aparato")
					colVistaTabla1Filtrada.cellValueFactory=cdf=>ObjectProperty(cdf.value.Aparato)
				
					val colVistaTabla2Filtrada = new TableColumn[filaDatos,Int]("Año")
					colVistaTabla2Filtrada.cellValueFactory=cdf=>ObjectProperty(cdf.value.Anio)

					val colVistaTabla3Filtrada = new TableColumn[filaDatos,Double]("Prediccion")
					colVistaTabla3Filtrada.cellValueFactory=cdf=>ObjectProperty(cdf.value.Prediccion)

					val tablaVistaFiltrada = new TableView(datosScalaFiltro)
					tablaVistaFiltrada.margin=Insets(0, 10, 10, 10)

					tablaVistaFiltrada.columns ++= List(colVistaTabla1Filtrada,colVistaTabla2Filtrada,colVistaTabla3Filtrada)
					pantallaPrincipal.center = tablaVistaFiltrada


				}

			pantallaPrincipal
			}
			
    }
  }
}
