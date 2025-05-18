import { CommonProjectsModel } from "./common-projects.model";

export interface PairModel {
  firstEmployee: number,
  secondEmployee: number,
  totalCommonDays: number,
  commonProjects: CommonProjectsModel[]
}