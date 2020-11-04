package com.ccfraser.muirwik.components

import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import styled.StyledHandler

@JsModule("@material-ui/lab/Alert")
private external val alertModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val alertComponent: RComponent<MAlertProps, RState> = alertModule.default

@Suppress("EnumEntryName")
enum class MAlertVariant {
  filled, outlined, standard
}

@Suppress("EnumEntryName")
enum class MAlertSeverity {
  error, info, success, warning
}

interface MAlertProps : StyledPropsWithCommonAttributes {
  var action: ReactElement
  var icon: ReactElement
  var onClose: (Event) -> Unit
  var closeText: String
}

var MAlertProps.variant by EnumPropToStringNullable(MAlertVariant.values())
var MAlertProps.severity by EnumPropToStringNullable(MAlertSeverity.values())

fun RBuilder.mAlert(
    message: String?,
    variant: MAlertVariant = MAlertVariant.filled,
    severity: MAlertSeverity = MAlertSeverity.success,
    closeText: String = "Close",
    onClose: ((Event) -> Unit)? = null,
    addAsChild: Boolean = true,

    className: String? = null,
    handler: StyledHandler<MAlertProps>? = null) = createStyled(alertComponent, addAsChild) {
  message?.let { +message }
  attrs.variant = variant
  attrs.severity = severity
  attrs.closeText = closeText
  onClose?.let { attrs.onClose = onClose }

  setStyledPropsAndRunHandler(className, handler)
}
