


JSR 380 (Java EE & Spring & JAX-RS 通用)	@NotNull, @NotEmpty, @NotBlank, @Size, @Min, @Max, @Email, @Pattern, @Future, @Past
Spring 专属	@Validated, @Valid, @RequestParam(required=false), @PathVariable(required=false), @RequestHeader(required=false)
JAX-RS 专属	@QueryParam, @PathParam, @FormParam, @Valid


private void validDto(Object dto) {

        var clazz = dto.getClass();
        // 遍历类的所有字段
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("字段: " + field.getName());

            // 遍历该字段上的所有注解
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                System.out.println("  注解: " + annotation.annotationType().getSimpleName());
                if (annotation.annotationType() == Min.class) {
                    MinValidator vldr = new MinValidator();
                    Min annotation1 = (Min) annotation;
                    vldr.initialize(annotation1);
                    if (!vldr.isValid((BigDecimal) getField(dto, field.getName()), null)) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("dto", dto);
                        m.put("fld", field.getName());
                        m.put("msg", "vldfail");
                        m.put("msgAnno",annotation1.message());
                        throw new RuntimeException(encodeJsonObj(m));
                    }
                    ;
                }
            }
        }

    }