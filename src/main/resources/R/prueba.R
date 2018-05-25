library(readr)
rawData <- read_csv("rawData.csv")
imprime<-function(x,y){ print(paste("Los valores son",x,"y",y,sep=" ")) }
ajusta<-function(df){df$Prediccion <- df$Anio+3.1416
return(df)}
modelo <- ajusta(rawData)

filtrar<-function(cadena,numero){
  df<-modelo
  if(cadena!="null" & cadena!="Todos") df<-df[which(df$Aparato==cadena),]
  if(numero!=0) df<-df[which(df$Anio==numero),]
  return(df)
}
