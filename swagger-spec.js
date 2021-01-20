window.swaggerSpec={
  "components" : {
    "schemas" : {
      "Specialty" : {
        "type" : "object",
        "required" : [ "name" ],
        "properties" : {
          "name" : {
            "type" : "string",
            "example" : "radiology"
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          }
        }
      },
      "Owner" : {
        "type" : "object",
        "required" : [ "address", "city", "firstName", "lastName", "telephone" ],
        "properties" : {
          "pets" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Pet"
            }
          },
          "firstName" : {
            "type" : "string",
            "example" : "George"
          },
          "lastName" : {
            "type" : "string",
            "example" : "Franklin"
          },
          "address" : {
            "type" : "string",
            "example" : "110 W. Liberty St."
          },
          "city" : {
            "type" : "string",
            "example" : "Madison"
          },
          "telephone" : {
            "type" : "string",
            "example" : "6085551023"
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          }
        }
      },
      "Vet" : {
        "type" : "object",
        "required" : [ "firstName", "lastName", "specialties" ],
        "properties" : {
          "firstName" : {
            "type" : "string",
            "example" : "James"
          },
          "lastName" : {
            "type" : "string",
            "example" : "Carter"
          },
          "specialties" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Specialty"
            }
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          }
        }
      },
      "Visit" : {
        "type" : "object",
        "required" : [ "description", "petId", "visitDate" ],
        "properties" : {
          "petId" : {
            "format" : "int32",
            "type" : "integer"
          },
          "description" : {
            "type" : "string",
            "example" : "rabies shot"
          },
          "visitDate" : {
            "format" : "date",
            "type" : "string",
            "example" : "2013-01-01"
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          }
        }
      },
      "Pet" : {
        "type" : "object",
        "required" : [ "birthDate", "name", "ownerId", "type" ],
        "properties" : {
          "visits" : {
            "type" : "array",
            "items" : {
              "$ref" : "#/components/schemas/Visit"
            }
          },
          "name" : {
            "type" : "string",
            "example" : "Leo"
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          },
          "ownerId" : {
            "format" : "int32",
            "type" : "integer"
          },
          "type" : {
            "$ref" : "#/components/schemas/PetType"
          },
          "birthDate" : {
            "format" : "date",
            "type" : "string",
            "example" : "2010-09-07"
          }
        }
      },
      "PetType" : {
        "type" : "object",
        "required" : [ "name" ],
        "properties" : {
          "name" : {
            "type" : "string",
            "example" : "cat"
          },
          "id" : {
            "format" : "int32",
            "type" : "integer"
          }
        }
      }
    }
  },
  "servers" : [ {
    "description" : "Generated server url",
    "url" : "http://localhost:8080"
  } ],
  "openapi" : "3.0.1",
  "paths" : {
    "/api/owners/{ownerId}/pets/{petId}/visits" : {
      "post" : {
        "summary" : "Create Visit",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Visit"
              }
            }
          },
          "required" : true
        },
        "operationId" : "createVisit",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Visit"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        } ],
        "tags" : [ "visits-controller" ]
      },
      "get" : {
        "summary" : "Finds Visits by ownerId and petId",
        "operationId" : "queryVisits",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Visit"
                  }
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        } ],
        "tags" : [ "visits-controller" ]
      }
    },
    "/api/owners/{ownerId}/pets" : {
      "post" : {
        "summary" : "Create Pet",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Pet"
              }
            }
          },
          "required" : true
        },
        "operationId" : "createPet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Pet"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        } ],
        "tags" : [ "pets-controller" ]
      },
      "get" : {
        "summary" : "Finds Pets by Owner id",
        "operationId" : "queryPets",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Pet"
                  }
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        } ],
        "tags" : [ "pets-controller" ]
      }
    },
    "/api/veterinarians" : {
      "post" : {
        "summary" : "Create Veterinarian",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Vet"
              }
            }
          },
          "required" : true
        },
        "operationId" : "createVet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Vet"
                }
              }
            }
          }
        },
        "tags" : [ "vets-controller" ]
      },
      "get" : {
        "summary" : "Finds Veterinarians",
        "operationId" : "queryVets",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Vet"
                  }
                }
              }
            }
          }
        },
        "tags" : [ "vets-controller" ]
      }
    },
    "/api/owners/{ownerId}/pets/{petId}/visits/{visitId}" : {
      "get" : {
        "summary" : "Find Visit by ownerId and petId and visitId",
        "operationId" : "showVisit",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Visit"
                }
              }
            }
          },
          "404" : {
            "description" : "Visit not found",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Visit"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "visitId",
          "required" : true
        } ],
        "tags" : [ "visits-controller" ]
      },
      "delete" : {
        "summary" : "Delete Visit",
        "operationId" : "deleteVisit",
        "responses" : {
          "200" : {
            "description" : "OK"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "visitId",
          "required" : true
        } ],
        "tags" : [ "visits-controller" ]
      },
      "put" : {
        "summary" : "Update Visit",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Visit"
              }
            }
          },
          "required" : true
        },
        "operationId" : "updateVisit",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Visit"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "visitId",
          "required" : true
        } ],
        "tags" : [ "visits-controller" ]
      }
    },
    "/api/veterinarians/{vetId}" : {
      "get" : {
        "summary" : "Find Veterinarian by id",
        "operationId" : "showVet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Vet"
                }
              }
            }
          },
          "404" : {
            "description" : "Veterinarian not found",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Vet"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "vetId",
          "required" : true
        } ],
        "tags" : [ "vets-controller" ]
      },
      "delete" : {
        "summary" : "Delete Veterinarian",
        "operationId" : "deleteVet",
        "responses" : {
          "200" : {
            "description" : "OK"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "vetId",
          "required" : true
        } ],
        "tags" : [ "vets-controller" ]
      },
      "put" : {
        "summary" : "Update Veterinarian",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Vet"
              }
            }
          },
          "required" : true
        },
        "operationId" : "updateVet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Vet"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "vetId",
          "required" : true
        } ],
        "tags" : [ "vets-controller" ]
      }
    },
    "/api/specialties" : {
      "post" : {
        "summary" : "Create Specialty",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Specialty"
              }
            }
          },
          "required" : true
        },
        "operationId" : "createSpecialty",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Specialty"
                }
              }
            }
          }
        },
        "tags" : [ "specialties-controller" ]
      },
      "get" : {
        "summary" : "Finds Specialties",
        "operationId" : "querySpecialties",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Specialty"
                  }
                }
              }
            }
          }
        },
        "tags" : [ "specialties-controller" ]
      }
    },
    "/api/owners/{ownerId}" : {
      "get" : {
        "summary" : "Find Owner by id",
        "operationId" : "showOwner",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Owner"
                }
              }
            }
          },
          "404" : {
            "description" : "Owner not found"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        } ],
        "tags" : [ "owners-controller" ]
      },
      "delete" : {
        "summary" : "Delete Owner",
        "operationId" : "deleteOwner",
        "responses" : {
          "200" : {
            "description" : "OK"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        } ],
        "tags" : [ "owners-controller" ]
      },
      "put" : {
        "summary" : "Update Owner",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Owner"
              }
            }
          },
          "required" : true
        },
        "operationId" : "updateOwner",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Owner"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        } ],
        "tags" : [ "owners-controller" ]
      }
    },
    "/api/owners/{ownerId}/pets/{petId}" : {
      "get" : {
        "summary" : "Find Pet by ownerId and petId",
        "operationId" : "showPet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Pet"
                }
              }
            }
          },
          "404" : {
            "description" : "Pet not found"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        } ],
        "tags" : [ "pets-controller" ]
      },
      "delete" : {
        "summary" : "Delete Pet",
        "operationId" : "deletePet",
        "responses" : {
          "200" : {
            "description" : "OK"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        } ],
        "tags" : [ "pets-controller" ]
      },
      "put" : {
        "summary" : "Update Pet",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Pet"
              }
            }
          },
          "required" : true
        },
        "operationId" : "updatePet",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Pet"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "ownerId",
          "required" : true
        }, {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "petId",
          "required" : true
        } ],
        "tags" : [ "pets-controller" ]
      }
    },
    "/api/specialties/{specialtyId}" : {
      "get" : {
        "summary" : "Find Specialty by id",
        "operationId" : "showSpecialty",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Specialty"
                }
              }
            }
          },
          "404" : {
            "description" : "Specialty not found",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Specialty"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "specialtyId",
          "required" : true
        } ],
        "tags" : [ "specialties-controller" ]
      },
      "delete" : {
        "summary" : "Delete Specialty",
        "operationId" : "deleteSpecialty",
        "responses" : {
          "200" : {
            "description" : "OK"
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "specialtyId",
          "required" : true
        } ],
        "tags" : [ "specialties-controller" ]
      },
      "put" : {
        "summary" : "Update Specialty",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Specialty"
              }
            }
          },
          "required" : true
        },
        "operationId" : "updateSpecialty",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Specialty"
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "format" : "int32",
            "type" : "integer"
          },
          "in" : "path",
          "name" : "specialtyId",
          "required" : true
        } ],
        "tags" : [ "specialties-controller" ]
      }
    },
    "/api/types" : {
      "get" : {
        "summary" : "Finds PetTypes",
        "operationId" : "queryPetTypes",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/PetType"
                  }
                }
              }
            }
          }
        },
        "tags" : [ "pet-types-controller" ]
      }
    },
    "/api/owners" : {
      "post" : {
        "summary" : "Create Owner",
        "requestBody" : {
          "content" : {
            "application/json" : {
              "schema" : {
                "$ref" : "#/components/schemas/Owner"
              }
            }
          },
          "required" : true
        },
        "operationId" : "createOwner",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/Owner"
                }
              }
            }
          }
        },
        "tags" : [ "owners-controller" ]
      },
      "get" : {
        "summary" : "Finds Owners by LastName",
        "operationId" : "queryOwners",
        "responses" : {
          "200" : {
            "description" : "OK",
            "content" : {
              "application/json" : {
                "schema" : {
                  "type" : "array",
                  "items" : {
                    "$ref" : "#/components/schemas/Owner"
                  }
                }
              }
            }
          }
        },
        "parameters" : [ {
          "schema" : {
            "type" : "string"
          },
          "in" : "query",
          "name" : "lastName",
          "required" : false
        } ],
        "tags" : [ "owners-controller" ]
      }
    }
  },
  "info" : {
    "title" : "petclinic-api",
    "version" : "0.0.1"
  }
}