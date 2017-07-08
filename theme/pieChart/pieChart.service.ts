import {Injectable} from '@angular/core';
// import { BaThemeConfigProvider } from '../../theme/theme.configProvider';

@Injectable()
export class PieChartService {

  constructor(/*private _baConfig: BaThemeConfigProvider*/) {
  }

  getData() {
    const pieColor = '#cdf73e';
    return [
      {
        color: pieColor,
        description: 'dashboard.new_visits',
        stats: '57,820',
        icon: 'person',
      }, {
        color: pieColor,
        description: 'dashboard.purchases',
        stats: '$ 89,745',
        icon: 'money',
      }, {
        color: pieColor,
        description: 'dashboard.active_users',
        stats: '178,391',
        icon: 'face',
      }, {
        color: pieColor,
        description: 'dashboard.returned',
        stats: '32,592',
        icon: 'refresh',
      }
    ];
  }
}
