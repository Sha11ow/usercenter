import React from 'react';
import { Button, Flex } from 'antd';

const App: React.FC = () => (
  <Flex gap="small" wrap="wrap">
    <Button type="link" href={'/user/login'}>用户</Button>
    <Button type="link" href={'/teacher/login'}>教师</Button>
    <Button type="link" href={'/student/login'}>学生</Button>
    <Button type="link" href={'/admin/login'}>管理员</Button>
  </Flex>
);

//这个export default App;是必须的，不然会报错。为什么这里是APP呢？因为在umijs中，一个页面就是一个组件，所以这里的APP就是一个页面
//可以换个名字吗？可以，但是要和文件名一样，不然会报错
export default App;
