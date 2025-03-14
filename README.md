# Idealista App - Prueba Técnica

Bienvenido a **La Prueba Técnica de Miguel Zaragoza para Idealista**, una aplicación diseñada para replicar la funcionalidad de la aplicación oficial, pero con un enfoque diferente en cuanto a diseño y experiencia de usuario. A lo largo de este proyecto, se ha procurado respetar el color corporativo original, a la vez que se ha presentado alternativas enfocadas especialmente a las animaciones para lograr mayor fluidez.

## Características

### Arquitectura
- **Arquitectura MVVM**: La aplicación sigue el patrón **Model-View-ViewModel (MVVM)**, lo que permite una separación clara de responsabilidades, facilitando su mantenimiento y escalabilidad. Además, se ha utilizado una **arquitectura monoactividad**.

- **Clean Code**: Se ha seguido el principio de **Clean Code** para asegurar que el código sea legible, mantenible y comprensible para otros desarrolladores. Se han evitado nombres ambiguos, funciones demasiado complejas y se ha buscado que cada clase, función y método tenga una única responsabilidad. Esto facilita la escalabilidad del proyecto y permite que cualquier cambio o mejora en el futuro sea realizado de manera sencilla.

- **Clean Architecture**: El proyecto se basa en los principios de **Clean Architecture**, que promueve una separación clara de las responsabilidades dentro de la aplicación, permitiendo que cada capa sea independiente y fácilmente modificable. Esto se logra mediante la creación de módulos bien definidos para la capa de presentación, la lógica de negocio y la gestión de datos, lo que asegura que las dependencias fluyan solo de manera unidireccional. Además, la arquitectura facilita la realización de pruebas unitarias y la integración de nuevas funcionalidades sin afectar el resto del sistema.

### Diseño de la Interfaz de Usuario
- **Jetpack Compose & XML**: Las vistas están principalmente en **Jetpack Compose**, lo que permite una experiencia de usuario fluida y dinámica gracias a sus animaciones nativas y su mayor flexibilidad en cuanto a diseño. También se ha utilizado **XML** en algunas secciones de la aplicación para mostrar ambas posibilidades.

- **Animaciones con Lottie**: Se ha integrado **Lottie** para animaciones de alta calidad, lo que mejora la experiencia visual y aporta dinamismo a la aplicación.

### Funcionalidades Clave
- **Persistencia de Datos**: Se ha implementado **Room** para la persistencia de los favoritos, guardando también la fecha de cada uno para una mejor gestión de la información.
  
- **Integración con Google Maps**: En el detalle de cada anuncio, se incluye un botón que redirige a la aplicación de **Google Maps** utilizando las coordenadas del anuncio, facilitando la localización directa de la propiedad en un mapa.

- **Cargar Imágenes con Coil**: Para optimizar la carga y visualización de imágenes, se ha integrado **Coil**, asegurando un rendimiento eficiente y fluido.

### Manejo de Datos
- **Gson, Moshi y OkHttp3**: Se han utilizado estas bibliotecas para manejar la conversión de datos JSON a objetos de transferencia de datos (DTO) y para realizar operaciones de red eficientes. **OkHttp3** gestiona las solicitudes HTTP, mientras que **Gson** y **Moshi** se encargan de la serialización y deserialización de los datos.

## Estructura del Proyecto

La aplicación está organizada en módulos para garantizar una arquitectura limpia y mantener una separación adecuada de responsabilidades:

1. **app**: El módulo principal que orquesta la integración de los módulos de datos, dominio y presentación.
2. **data**: Maneja las operaciones relacionadas con los datos, incluyendo el acceso a la red y la base de datos local.
3. **domain**: Contiene la lógica de negocio y los casos de uso, funcionando como el núcleo de la aplicación.
4. **presentation**: Encargado de la interfaz de usuario y la lógica de presentación, utilizando Jetpack Compose y XML según sea necesario.
5. **core**: Un módulo customizado que contiene utilidades y componentes compartidos, lo que permite facilitar el desarrollo y la reutilización del código en diferentes aplicaciones. Aunque no se utilizó tanto como se esperaba inicialmente, este módulo proporciona una base sólida y flexible para cualquier aplicación, especialmente en cuanto a componentes se refiere.

## Dependencias y Tecnologías

- **Koin**: Se ha utilizado **Koin** para la inyección de dependencias, promoviendo la modularidad y facilitando las pruebas unitarias.
- **Coroutines**: La aplicación hace uso de **Coroutines** para gestionar las operaciones asincrónicas, mejorando la eficiencia y la capacidad de respuesta de la app.
- **Terceros Integrados**:
  - **Coil** para la carga de imágenes.
  - **Lottie** para animaciones.
  - **Gson** y **Moshi** para la serialización de datos.
  - **OkHttp3** para operaciones de red.
 
 ## Pantallas

### MainFragment
- *Scaffold* que da lugar a una TopBar y una BottomNavigation con dos opciones para cambiar entre Screens.
- *FloatingActionButton* para el filtro.

 ### HomeScreen
 - *VerticalPager* para mostrar la lista de los anuncios donde aparece un resumen del anuncio.
 - Botón para guardar o quitar un anuncio como favorito.
 - Haciendo click en cada anuncio se navegaría al DetailFragment.

### FavoritesScreen
 - Listado vertical para agrupar los ads favoritos por días guardados.
 - Listado horizontal para mostrar todos los guardados ese día.
 - Haciendo click en el anuncio se navega al DetailFragment.

### DetailFragment
 - *HorizontalPager* para mostrar todas las imágenes del anuncio.
 - Se amplía la información del anuncio.
 - Se cuenta con un botón para mostrar la ubicación del anuncio a través de la app Google Maps.
 - Se cuenta con un botón para guardar o quitar un anuncio como favorito.

## Navegación

- Se implementa una navegación en el *IdealistaActivity* para navegar entre *Fragments* y poder hacerlo entre el *MainFragment* y el *DetailFragment*.
- Se implementa una navegación en el *MainFragment* a partir de una *BottomNavigation* para poder navegar entre el *HomeScreen* y el *FavoritesScreen*. 

## Pruebas

Se han implementado pruebas de diferentes tipos para mostrar el manejo de las mismas por la aplicación:

- **Pruebas Unitarias**: Se han cubierto las funcionalidades base como la recepción de datos de un **ViewModel** y las llamadas de los **casos de uso**.
- **Pruebas Instrumentadas**: Se ha realizado pruebas sobre la *base de datos de favoritos de Room*, probando a añadir, obtener y eliminar los favoritos.
- **Pruebas de UI**: Se ha creado una prueba de **captura de pantalla (screenshot)** para validar que la interfaz de usuario se muestra correctamente en su estado inicial.

## Agradecimientos

Gracias por dedicar vuestro tiempo a revisar este proyecto. Espero que haya sido de vuestro agrado y quedo a la espera de cualquier feedback que queráis darme.
Miguel.
