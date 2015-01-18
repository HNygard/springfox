package com.mangofactory.swagger.mixins
import com.fasterxml.classmate.TypeResolver
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.mangofactory.schema.DefaultGenericTypeNamingStrategy
import com.mangofactory.schema.DefaultModelProvider
import com.mangofactory.schema.ModelDependencyProvider
import com.mangofactory.schema.ModelProvider
import com.mangofactory.schema.TypeNameExtractor
import com.mangofactory.schema.property.ObjectMapperBeanPropertyNamingStrategy
import com.mangofactory.schema.alternates.AlternateTypeProvider
import com.mangofactory.schema.property.bean.AccessorsProvider
import com.mangofactory.schema.property.bean.BeanModelPropertyProvider
import com.mangofactory.schema.property.constructor.ConstructorModelPropertyProvider
import com.mangofactory.schema.property.field.FieldModelPropertyProvider
import com.mangofactory.schema.property.field.FieldProvider
import com.mangofactory.schema.property.provider.DefaultModelPropertiesProvider

@SuppressWarnings("GrMethodMayBeStatic")
@Mixin(PluginsSupport)
class ModelProviderForServiceSupport {
  def typeNameExtractor() {
    new TypeNameExtractor(new TypeResolver(), new DefaultGenericTypeNamingStrategy(), pluginsManager())
  }

  ModelProvider modelProvider(TypeResolver typeResolver = new TypeResolver(),
                              AlternateTypeProvider alternateTypeProvider = new AlternateTypeProvider()) {

    def fields = new FieldProvider(typeResolver)
    def pluginsManager = pluginsManager()
    def objectMapper = new ObjectMapper()
    def typeNameExtractor = typeNameExtractor()
    def namingStrategy = new ObjectMapperBeanPropertyNamingStrategy(objectMapper)

    def beanModelPropertyProvider = new BeanModelPropertyProvider(new AccessorsProvider(typeResolver), typeResolver,
            alternateTypeProvider, namingStrategy, pluginsManager, typeNameExtractor)
    def fieldModelPropertyProvider = new FieldModelPropertyProvider(fields, alternateTypeProvider, namingStrategy,
            pluginsManager, typeNameExtractor)
    def constructorModelPropertyProvider =
            new ConstructorModelPropertyProvider(fields, alternateTypeProvider, namingStrategy, pluginsManager, typeNameExtractor)

    def modelPropertiesProvider = new DefaultModelPropertiesProvider(beanModelPropertyProvider,
            fieldModelPropertyProvider, constructorModelPropertyProvider)

    modelPropertiesProvider.objectMapper = objectMapper
    def modelDependenciesProvider = new ModelDependencyProvider(typeResolver, alternateTypeProvider,
            modelPropertiesProvider, typeNameExtractor)
    new DefaultModelProvider(typeResolver, alternateTypeProvider, modelPropertiesProvider, modelDependenciesProvider,
            pluginsManager, typeNameExtractor)
  }

  ModelProvider modelProviderWithSnakeCaseNamingStrategy(TypeResolver typeResolver = new TypeResolver(),
                              AlternateTypeProvider alternateTypeProvider = new AlternateTypeProvider()) {

    def fields = new FieldProvider(typeResolver)
    def pluginsManager = pluginsManager()
    def objectMapper = new ObjectMapper()
    def typeNameExtractor = typeNameExtractor()
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
    def namingStrategy = new ObjectMapperBeanPropertyNamingStrategy(objectMapper)

    def beanModelPropertyProvider = new BeanModelPropertyProvider(new AccessorsProvider(typeResolver), typeResolver,
            alternateTypeProvider, namingStrategy, pluginsManager, typeNameExtractor)
    def fieldModelPropertyProvider = new FieldModelPropertyProvider(fields, alternateTypeProvider, namingStrategy,
            pluginsManager, typeNameExtractor)
    def constructorModelPropertyProvider =
            new ConstructorModelPropertyProvider(fields, alternateTypeProvider, namingStrategy, pluginsManager,
                    typeNameExtractor)

    def modelPropertiesProvider = new DefaultModelPropertiesProvider(beanModelPropertyProvider,
            fieldModelPropertyProvider, constructorModelPropertyProvider)

    modelPropertiesProvider.objectMapper = objectMapper
    def modelDependenciesProvider = new ModelDependencyProvider(typeResolver, alternateTypeProvider,
            modelPropertiesProvider, typeNameExtractor)
    new DefaultModelProvider(typeResolver, alternateTypeProvider, modelPropertiesProvider, modelDependenciesProvider,
            pluginsManager, typeNameExtractor)
  }


}