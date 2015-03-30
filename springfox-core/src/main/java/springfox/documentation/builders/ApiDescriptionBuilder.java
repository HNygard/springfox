/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.builders;

import com.google.common.collect.Ordering;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;

import java.util.List;

public class ApiDescriptionBuilder {
  private String path;
  private String description;
  private List<Operation> operations;
  private Ordering<Operation> operationOrdering;
  private Boolean hidden;

  public ApiDescriptionBuilder(Ordering<Operation> operationOrdering) {
    this.operationOrdering = operationOrdering;
  }

  public ApiDescriptionBuilder path(String path) {
    this.path = BuilderDefaults.defaultIfAbsent(path, this.path);
    return this;
  }

  public ApiDescriptionBuilder description(String description) {
    this.description = BuilderDefaults.defaultIfAbsent(description, this.description);
    return this;
  }

  public ApiDescriptionBuilder operations(List<Operation> operations) {
    if (operations != null) {
      this.operations = operationOrdering.sortedCopy(operations);
    }
    return this;
  }

  public ApiDescriptionBuilder hidden(boolean hidden) {
    this.hidden = hidden;
    return this;
  }

  public ApiDescription build() {
    return new ApiDescription(path, description, operations, hidden);
  }
}